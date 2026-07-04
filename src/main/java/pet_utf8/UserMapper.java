package pet_utf8;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User login(@Param("username") String username,
               @Param("password") String password);
}