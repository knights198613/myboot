package com.jiangwei.springboottest.myboot.api;

//import com.cxyx.digger.agent.monitor.Digger;
import com.google.common.collect.Sets;
import com.jiangwei.springboottest.myboot.domains.Student;
import com.jiangwei.springboottest.myboot.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: weijiang
 * @date: 2022/1/10
 * @description:
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @PostMapping("/getById")
    @ResponseBody
    public Student getStudentById(Integer id) {
        String traceId = "2348398494238jiueiuriewuiui898989";
        //Digger.buildDigger(traceId, this.getClass(), "getStudentById");
        Student student = null;
        try {
            if(id.intValue() % 2 == 0) {
                throw new RuntimeException("故意制造异常模拟数据！");
            }
            student = studentService.getStudentById(id);
            /*Digger.success(Sets.newHashSet(student), Sets.newHashSet(id),
                    "查询学生", "通过学生id查询学生信息", null);*/

        } catch (Exception e) {
            e.printStackTrace();
            /*Digger.error("40001", "查询学生发生异常",
                    e, "查询学生", "通过学生id查询学生信息");*/
        } finally {
            /*Digger.finished();*/
        }
        return student;
    }


}
