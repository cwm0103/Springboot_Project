package com.oper.repository;

import com.oper.entity.PubDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: jindb
 * @Date: 2018/8/7 15:34
 * @Description:
 */
public interface PubDirectoryRepository  extends JpaRepository<PubDirectory,Integer> {


    List<PubDirectory> findByDirectoryName(String directoryName);

    Boolean existsByDirectoryName(String directoryName);

    Boolean existsByDirectoryNameAndDirectoryIdIsNot(String directoryName,Integer directoryId);

    @Query(value = "update pub_directory set directory_name=?1,directory_desc=?2 where directory_id=?3 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateOne(String name,String desc,Integer id);

    @Query(value = "update pub_directory set pid=?1 where directory_id=?2 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void move(Integer pid,Integer id);

    //根据id来获取目录
    PubDirectory findPubDirectoryByDirectoryId(Integer id);
    //查找所有目录
    List<PubDirectory>  findAll();

}
