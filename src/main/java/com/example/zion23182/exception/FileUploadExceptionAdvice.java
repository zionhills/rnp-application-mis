package com.example.zion23182.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
    model.addAttribute("message", "File is too large!");

    return "./Dashboard/upload";
  }
}
