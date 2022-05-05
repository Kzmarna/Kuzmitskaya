package com.coursework.service;

import com.coursework.model.Student;


import java.awt.*;
import java.io.File;
import java.util.List;

public interface FileService {
    File createFile(Container container);
    void writeDataToWord(File file, List<Student> students);
}
