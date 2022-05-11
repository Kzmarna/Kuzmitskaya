package com.coursework.service;

import com.coursework.model.Student;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    File createFile(Container container);
    void writeDataToExcel(File file, List<Student> students, String group) throws IOException;
}
