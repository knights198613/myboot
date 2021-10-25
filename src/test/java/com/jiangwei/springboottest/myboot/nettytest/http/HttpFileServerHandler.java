package com.jiangwei.springboottest.myboot.nettytest.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.internal.SystemPropertyUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * @author: weijiang
 * @date: 2021/10/25
 * @description:
 **/
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;

    private FullHttpRequest httpRequest;

    private String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        this.httpRequest = request;
        if(!request.decoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }
        if(request.method() != HttpMethod.GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        final String uri = request.uri();
        final String path = parseURI(uri);
        if(path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }

        File file = new File(path);
        if(file.isHidden() && !file.exists()) {
            sendError(ctx, NOT_FOUND);
            return;
        }

        if(file.isDirectory()) {
            if(path.endsWith("/")) {
                sendList(ctx, file, path);
            }else {
                sendRedirect(ctx, path+"/");
            }
            return;
        }
        if(!file.isFile()) {
            sendError(ctx, FORBIDDEN);
            return;
        }

        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            sendError(ctx, NOT_FOUND);
            return;
        }

        long fileLength = randomAccessFile.length();
        HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, OK);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
        setHeaderContentType(httpResponse, file);
        setHeadersDateAndCache(httpResponse, file);
        if(!keepAlive) {
            httpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }else if(this.httpRequest.protocolVersion().equals(HttpVersion.HTTP_1_0)){
            httpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        //写回response的header信息
        ctx.write(httpResponse);
        ChannelFuture sendFuture = ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(randomAccessFile, 0, fileLength, 8192)),
                ctx.newProgressivePromise());
        sendFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if(total < 0) {
                    System.err.println(future.channel()+"Transfer progress:"+progress);
                }else {
                    System.out.println(future.channel()+"Transfer progress:" + progress + "/" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println(future.channel()+"Transfer completely!");
            }
        });
        if(!keepAlive) {
            sendFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }


    /**
     * 设置http 请求的header中日期和缓存
     * @param httpResponse
     * @param file
     */
    private void setHeadersDateAndCache(HttpResponse httpResponse, File file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar calendar = new GregorianCalendar();
        httpResponse.headers().set(HttpHeaderNames.DATE, simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        httpResponse.headers().set(HttpHeaderNames.EXPIRES, simpleDateFormat.format(calendar.getTime()));
        httpResponse.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age="+HTTP_CACHE_SECONDS);
        httpResponse.headers().set(HttpHeaderNames.LAST_MODIFIED, simpleDateFormat.format(new Date(file.lastModified())));
    }

    /**
     * 设置http请求的 content-type
     * @param httpResponse
     * @param file
     */
    private void setHeaderContentType(HttpResponse httpResponse, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file));
    }

    /**
     * 列出文件目录
     * @param ctx
     * @param file
     * @param path
     */
    private void sendList(ChannelHandlerContext ctx, File file, String path) {

    }

    /**
     * 文件目录跳转
     * @param ctx
     * @param path
     */
    private void sendRedirect(ChannelHandlerContext ctx, String path) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, FOUND, Unpooled.EMPTY_BUFFER);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        sendAndCleanUpConnection(ctx, httpResponse);
    }


    private void sendAndCleanUpConnection(ChannelHandlerContext ctx, FullHttpResponse response) {
        final FullHttpRequest request = this.httpRequest;
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        HttpUtil.setContentLength(response, response.content().readableBytes());
        if(!keepAlive) {
            HttpUtil.setKeepAlive(response, false);
        }else if(request.protocolVersion().equals(HttpVersion.HTTP_1_0)) {
            HttpUtil.setKeepAlive(response.headers(), HttpVersion.HTTP_1_0,true);
        }
        ChannelFuture channelFuture =  ctx.writeAndFlush(response);

        if(!keepAlive) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }

    /**
     * 统一的请求错误输出方法
     * @param ctx
     * @param status
     */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ctx.writeAndFlush(status.toString());
    }





    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * 对请求uri 进行编码解析出文件路径
     * @param uri
     * @return
     */
    private String parseURI(String uri) {
        String tmpUri = "";
        try {
            tmpUri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            try {
                tmpUri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
                throw new Error(ex);
            }
        }

        if(tmpUri == null || tmpUri.charAt(0) != '/') {
            return null;
        }
        tmpUri = tmpUri.replace('/', File.separatorChar);
        if(tmpUri.contains("."+File.separator) || tmpUri.contains(File.separator+".")
                || tmpUri.charAt(0) =='.' || tmpUri.charAt(tmpUri.length()-1) == '.'
                || INSECURE_URI.matcher(tmpUri).matches()) {
            return null;
        }

        return SystemPropertyUtil.get("user.dir")+File.separator+tmpUri;
    }
}
