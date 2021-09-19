package soso.sosoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @GetMapping("index")
    public String index() {
        return "admin/admin-index";
    }

    //component
    @GetMapping("component/alert")
    public String alert() {
        return "admin/component-alert";
    }

    @GetMapping("component/button")
    public String button() {
        return "admin/component-button";
    }

    @GetMapping("/component/pagination")
    public String pagination() {
        return "admin/component-pagination";
    }

    //extra-component
    @GetMapping("/extracomponent/sweetalert")
    public String sweetalert() {
        return "admin/extra-component-sweetalert";
    }

    @GetMapping("/extracomponent/toastify")
    public String toastify() {
        return "admin/extra-component-toastify";
    }


    //form
    @GetMapping("/form/input")
    public String input() {
        return "admin/form-element-input";
    }

    @GetMapping("/form/input-group")
    public String inputGroup() {
        return "admin/form-element-input-group";
    }

    @GetMapping("/form/select")
    public String select() {
        return "admin/form-element-select";
    }

    @GetMapping("/form/radio")
    public String radio() {
        return "admin/form-element-radio";
    }

    @GetMapping("/form/checkbox")
    public String checkbox() {
        return "admin/form-element-checkbox";
    }

    @GetMapping("/form/textarea")
    public String textarea() {
        return "admin/form-element-textarea";
    }

    @GetMapping("/form/layout")
    public String layout() {
        return "admin/form-layout";
    }

    @GetMapping("/form/editor")
    public String editor() {
        return "admin/form-editor-summernote";
    }


    //table
    @GetMapping("/table")
    public String table() {
        return "admin/table";
    }

    @GetMapping("/datatable")
    public String datatable() {
        return "admin/table-datatable";
    }

    //widget
    @GetMapping("/widgets/chatbox")
    public String chatbox() {
        return "admin/ui-widgets-chatbox";
    }

    @GetMapping("/widgets/todolist")
    public String toDoList() {
        return "admin/ui-widgets-todolist";
    }

    @GetMapping("/fileupload")
    public String fileUpLoad() {
        return "admin/ui-file-uploader";
    }


    //auth
    @GetMapping("/login")
    public String authLogin() {
        return "admin/admin-login";
    }

    @GetMapping("/auth/register")
    public String authRegister() {
        return "admin/auth-register";
    }

    @GetMapping("/auth/password")
    public String authPassword() {
        return "admin/auth-forgot-password";
    }
}
