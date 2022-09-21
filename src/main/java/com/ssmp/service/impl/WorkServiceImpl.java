package com.ssmp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssmp.dao.WorkDao;
import com.ssmp.pojo.Attendance;
import com.ssmp.pojo.Employee;
import com.ssmp.pojo.File;
import com.ssmp.pojo.Work;
import com.ssmp.service.IEmployeeService;
import com.ssmp.service.IWorkService;
import com.ssmp.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl extends ServiceImpl<WorkDao, Work> implements IWorkService {
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private WorkDao workDao;
    @Override
    public IPage<Work> getPage(int currentPage, int pageSize) {
        IPage<Work> iPage = new Page<>(currentPage,pageSize);
        page(iPage);
        //遍历一遍，加上外键和计算分钟
        for (Work Work : iPage.getRecords()) {
            addForeign(Work);
        }
        return iPage;
    }

    @Override
    public Work findById(Integer id) {
        Work Work = getById(id);
        addForeign(Work);
        return Work;
    }

    @Override
    public List<Work> findReceived(Integer employeeId) {
//        LambdaQueryWrapper<Work> lqw = new LambdaQueryWrapper<>();
//        lqw.orderByDesc(Work::getWorkTime);
//        lqw.eq(Work::getWorkTo, employeeId);
//        List<Work> list = list(lqw);
//        for (Work Work : list) {
//            addForeign(Work);
//        }
//        return list;
        List<Work> list = workDao.findReceiveWithForeign(employeeId);
        for (Work work : list) {
            work.setWorkTo(work.getWorkToEmployee().getEmployeeId());
            work.setEmployeeId(work.getEmployee().getEmployeeId());
        }
        return list;
    }

    @Override
    public List<Work> findSend(Integer employeeId) {
//        LambdaQueryWrapper<Work> lqw = new LambdaQueryWrapper<>();
//        lqw.orderByDesc(Work::getWorkTime);
//        lqw.eq(Work::getEmployeeId, employeeId);
//        List<Work> list = list(lqw);
//        for (Work Work : list) {
//            addForeign(Work);
//        }
//        return list;
        List<Work> list = workDao.findSendWithForeign(employeeId);
        for (Work work : list) {
            work.setWorkTo(work.getWorkToEmployee().getEmployeeId());
            work.setEmployeeId(work.getEmployee().getEmployeeId());
        }
        return list;
    }

    @Override
    public IPage<Work> getReceivePage(int currentPage, int pageSize,Work work1) {
        IPage<Work> iPage = new Page<>(currentPage,pageSize);
        workDao.pageReceiveWithForeign(iPage, SessionUtil.getEmployee().getEmployeeId());
        for (Work work : iPage.getRecords()) {
            work.setWorkTo(work.getWorkToEmployee().getEmployeeId());
            work.setEmployeeId(work.getEmployee().getEmployeeId());
        }
        return iPage;
    }

    @Override
    public IPage<Work> getSendPage(int currentPage, int pageSize,Work work1) {
        IPage<Work> iPage = new Page<>(currentPage,pageSize);
        workDao.pageSendWithForeign(iPage, SessionUtil.getEmployee().getEmployeeId());
        for (Work work : iPage.getRecords()) {
            work.setWorkTo(work.getWorkToEmployee().getEmployeeId());
            work.setEmployeeId(work.getEmployee().getEmployeeId());
        }
        return iPage;
    }

    private void addForeign(Work Work) {
        Work.setEmployee(employeeService.findById(Work.getEmployeeId()));
        Work.setWorkToEmployee(employeeService.findById(Work.getWorkTo()));
    }
}
