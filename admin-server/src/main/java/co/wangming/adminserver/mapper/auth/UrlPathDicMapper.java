package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.UrlPathDic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface UrlPathDicMapper {

    @Select("SELECT * FROM UrlPathDic")
    List<UrlPathDic> selectAllUrlPathDic();

    @Insert("INSERT INTO UrlPathDic (path, pathName, status) VALUES(#{path}, #{pathName}, 1)")
    int insertOneUrlPathDic(UrlPathDic user);
}
