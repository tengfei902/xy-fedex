package com.xy.fedex.admin.service.dsn.impl;

import com.google.gson.Gson;
import com.xy.fedex.admin.dao.DsnDao;
import com.xy.fedex.admin.enums.DsnStatus;
import com.xy.fedex.admin.exception.DsnNotFoundException;
import com.xy.fedex.admin.service.datasource.ConnectPreCheck;
import com.xy.fedex.admin.service.dsn.DsnService;
import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import com.xy.fedex.admin.service.dsn.model.po.DsnPO;
import com.xy.infrustruction.definition.DbType;
import com.xy.infrustruction.definition.connection.ConnectInfo;
import com.xy.infrustruction.definition.connection.ConnectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DsnServiceImpl implements DsnService {
    @Autowired
    private DsnDao dsnDao;

    /**
     * 创建dsn
     * 1.检查用户权限
     * 2.检查dsn连接是否成功
     * 3.保存dsn
     * 4.创建连接池
     * @param dsn
     */
    @Override
    public DsnDTO createDsn(DsnDTO dsn) {
        ConnectPreCheck.preCheck(null,dsn.getConnectInfo());
        saveOrUpdateDsn(dsn);
        return getDsn(dsn.getDsnName());
    }

    private Long saveOrUpdateDsn(DsnDTO dsn) {
        if(Objects.isNull(dsn.getDsnId())) {
            return saveDsn(dsn);
        }
        DsnPO existedDsn = dsnDao.selectByPrimaryKey(dsn.getDsnId());
        if(Objects.isNull(existedDsn)) {
            throw new DsnNotFoundException("dsn not found,id:"+dsn.getDsnId());
        }
        return updateDsn(dsn);
    }

    private Long saveDsn(DsnDTO dsn) {
        DsnPO po = new DsnPO();
        po.setDsn(dsn.getDsnName());
        po.setDbType(dsn.getDbType().name());
        po.setConnectInfo(new Gson().toJson(dsn.getConnectInfo()));
        po.setCreator(dsn.getCreator());
        dsnDao.insertSelective(po);
        return po.getId();
    }

    private Long updateDsn(DsnDTO dsn) {
        DsnPO po = new DsnPO();
        po.setId(dsn.getDsnId());
        po.setDsn(dsn.getDsnName());
        po.setConnectInfo(new Gson().toJson(dsn.getConnectInfo()));
        po.setStatus(DsnStatus.NEW.getCode());
        dsnDao.updateByPrimaryKeySelective(po);
        return po.getId();
    }

    @Override
    public void checkDsn(ConnectInfo connectInfo) {

    }

    @Override
    public DsnDTO getDsn(String dsn) {
        DsnPO dsnPO = dsnDao.selectByDsnName(dsn);
        if(Objects.isNull(dsnPO)) {
            throw new DsnNotFoundException("dsn not found:"+dsn);
        }
        DbType dbType = DbType.parse(dsnPO.getDbType());
        return DsnDTO.builder().dsnId(dsnPO.getId()).dsnName(dsnPO.getDsn()).connectInfo(ConnectUtils.getConnectInfo(dbType,dsnPO.getConnectInfo())).creator(dsnPO.getCreator()).dbType(null).build();
    }

    @Override
    public DsnDTO getDsn(Long dsnId) {
        return null;
    }

    @Override
    public void deleteDsn(Long dsnId) {
    }
}
