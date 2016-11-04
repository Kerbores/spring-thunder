package club.zhcs.thunder.listener;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import club.zhcs.thunder.biz.acl.PermissionService;
import club.zhcs.thunder.biz.acl.RolePermissionService;
import club.zhcs.thunder.biz.acl.RoleService;
import club.zhcs.thunder.biz.acl.UserRoleService;
import club.zhcs.thunder.biz.acl.UserService;
import club.zhcs.thunder.domain.InstallPermission;
import club.zhcs.thunder.domain.InstalledRole;
import club.zhcs.thunder.domain.acl.Permission;
import club.zhcs.thunder.domain.acl.Role;
import club.zhcs.thunder.domain.acl.RolePermission;
import club.zhcs.thunder.domain.acl.User;
import club.zhcs.thunder.domain.acl.User.Status;
import club.zhcs.thunder.domain.acl.UserRole;
import club.zhcs.thunder.ext.spring.SpringBeans;

/**
 * 
 * @author admin
 *
 * @email kerbores@gmail.com
 *
 */
public class ApplicationInitRunner implements ApplicationListener<ContextRefreshedEvent> {

	Logger log = Logger.getLogger(getClass());

	Role admin;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		SpringBeans.applicationContext = context;
		if (context.getParent() == null) {// 保险一点儿
			Dao dao = context.getBean(Dao.class);
			Daos.createTablesInPackage(dao, "club.zhcs", false);// 初始化一下
			Daos.migration(dao, "club.zhcs", true, true);
			initAcl(context);
		}
	}

	/**
	 * @param context
	 */
	private void initAcl(ApplicationContext context) {
		log.debug("init acl...");
		final UserService userService = context.getBean(UserService.class);
		final RoleService roleService = context.getBean(RoleService.class);
		final PermissionService permissionService = context.getBean(PermissionService.class);
		final UserRoleService userRoleService = context.getBean(UserRoleService.class);
		final RolePermissionService rolePermissionService = context.getBean(RolePermissionService.class);

		Lang.each(InstalledRole.values(), new Each<InstalledRole>() {// 内置角色

			@Override
			public void invoke(int index, InstalledRole role, int length) throws ExitLoop, ContinueLoop, LoopException {
				if (roleService.fetch(Cnd.where("name", "=", role.getName())) == null) {
					Role temp = new Role();
					temp.setName(role.getName());
					temp.setDescription(role.getDescription());
					roleService.save(temp);
				}
			}
		});

		admin = roleService.fetch(Cnd.where("name", "=", InstalledRole.SU.getName()));

		if (admin == null) {// 这里理论上是进不来的,防止万一吧
			admin = new Role();
			admin.setName(InstalledRole.SU.getName());
			admin.setDescription(InstalledRole.SU.getDescription());
			admin = roleService.save(admin);
		}

		Lang.each(InstallPermission.values(), new Each<InstallPermission>() {// 内置权限

			@Override
			public void invoke(int index, InstallPermission permission, int length) throws ExitLoop, ContinueLoop, LoopException {
				Permission temp = null;
				if ((temp = permissionService.fetch(Cnd.where("name", "=", permission.getName()))) == null) {
					temp = new Permission();
					temp.setName(permission.getName());
					temp.setDescription(permission.getDescription());
					temp = permissionService.save(temp);
				}

				// 给SU授权
				if (rolePermissionService.fetch(Cnd.where("permissionId", "=", temp.getId()).and("roleId", "=", admin.getId())) == null) {
					RolePermission rp = new RolePermission();
					rp.setRoleId(admin.getId());
					rp.setPermissionId(temp.getId());
					rolePermissionService.save(rp);
				}
			}
		});

		User surperMan = null;
		if ((surperMan = userService.fetch(Cnd.where("name", "=", "admin"))) == null) {
			surperMan = new User();
			surperMan.setEmail("kerbores@zhcs.club");
			surperMan.setName("admin");
			surperMan.setPassword(Lang.md5("123456"));
			surperMan.setPhone("18996359755");
			surperMan.setRealName("Kerbores");
			surperMan.setNickName("Kerbores");
			surperMan.setStatus(Status.ACTIVED);
			surperMan = userService.save(surperMan);
		}

		UserRole ur = null;
		if ((ur = userRoleService.fetch(Cnd.where("userId", "=", surperMan.getId()).and("roleId", "=", admin.getId()))) == null) {
			ur = new UserRole();
			ur.setUserId(surperMan.getId());
			ur.setRoleId(admin.getId());
			userRoleService.save(ur);
		}
	}

}
