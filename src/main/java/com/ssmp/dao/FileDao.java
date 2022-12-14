package com.ssmp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssmp.pojo.Attendance;
import com.ssmp.pojo.Employee;
import com.ssmp.pojo.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDao extends BaseMapper<File> {
    /**
     *
     * 多表联查，查询所有
     * @return List
     */
    List<File> findReceiveWithForeign(Integer id);
    /**
     *
     * 多表联查，查询所有
     * @return List
     */
    List<File> findSendWithForeign(Integer id);

    /**
     * 多表联查，自定义分页-收信箱

     * @param id
     * @return
     */
    IPage<File> pageReceiveWithForeign(IPage<File> iPage, Integer id);

    /**
     * 多表联查，自定义分页-发信箱

     * @param id
     * @return
     */
    List<File> pageSendWithForeign(IPage<File> page,Integer id);

}
