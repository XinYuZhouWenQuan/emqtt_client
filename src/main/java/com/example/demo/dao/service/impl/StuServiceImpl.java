package com.example.demo.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.StuMapper;
import com.example.demo.dao.service.StuService;
import com.example.demo.pojo.Stu;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {
}
