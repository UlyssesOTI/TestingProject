package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.dao.RolesDao;
import ua.lviv.lgs.dto.RoleDTO;
import ua.lviv.lgs.entity.Roles;
import ua.lviv.lgs.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesDao rolesDao;

//	@Transactional
//	public void add(String roleName) {
//		Roles role = new Roles(roleName);
//		rolesDao.save(role);
//	}
//
//	@Transactional
//	public void edit(int id, String roleName) {
//		Roles role = rolesDao.findOne(id);
//		if (roleName != null) {
//			role.setName(roleName);
//		}
//	}
//
//	@Transactional
//	public void delete(int id) {
//		Roles role = rolesDao.findOne(id);
//		rolesDao.delete(role);
//	}

	@Transactional
	public List<RoleDTO> findAll() {
		List<Roles> roles = rolesDao.findAll();
		List<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();
		for (Roles role : roles) {
			RoleDTO roleDTO = new RoleDTO(role.getId(),role.getName());
			rolesDTO.add(roleDTO);
		}
		Collections.sort(rolesDTO, new Comparator<RoleDTO>() {

			public int compare(RoleDTO o1, RoleDTO o2) {
				// TODO Auto-generated method stub
				return o1.getId() - o2.getId();
			}
		});
		return rolesDTO;
	}

//	@Transactional
//	public RoleDTO findById(int id) {
//		Roles role = rolesDao.findOne(id);
//		RoleDTO roleDTO = new RoleDTO(role.getId(),role.getName());
//		return roleDTO;
//	}
}
