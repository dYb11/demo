package com.example.demo.search.dao;

import com.example.demo.search.domain.EsProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Dao
 * Created by macro on 2018/6/19.
 */
@Mapping
@Repository
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
