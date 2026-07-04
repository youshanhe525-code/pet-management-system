package pet_utf8;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetMapper {
    List<Pet> selectAll();
    int insert(Pet pet);
    int update(Pet pet);
    int delete(@Param("pid") int pid);
}