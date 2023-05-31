package com.lhf.dajiuye.mapper.acl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.lhf.dajiuye.api.bean.acl.Permission;
import com.lhf.dajiuye.bean.acl.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    @Select("    \tselect\n" +
            "        p.permission_value\n" +
            "        from acl_user_role ur\n" +
            "        inner join acl_role_permission rp on rp.role_id = ur.role_id\n" +
            "        inner join acl_permission p on p.id = rp.permission_id\n" +
            "        where ur.user_id = #{id}\n" +
            "        and p.type = 2\n" +
            "        and ur.is_deleted = 0\n" +
            "        and rp.is_deleted = 0\n" +
            "        and p.is_deleted = 0" +
            "")
    List<String> selectPermissionValueByUserId(String id);

    @Select("select permission_value " +
            "from acl_permission " +
            "where type = 2 " +
            "and is_deleted = 0")
    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
