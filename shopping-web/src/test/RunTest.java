import base.TestSupport;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edaochina.shopping.api.dao.order.OrderMainMapper;
import com.edaochina.shopping.api.dao.user.*;
import com.edaochina.shopping.common.utils.http.HttpClientUtilsTool;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.sys.SysAddress;
import com.edaochina.shopping.domain.entity.user.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : test
 * @since : 2019/7/8 15:58
 */
public class RunTest extends TestSupport {

    @Autowired
    SysAddressMapper sysAddressMapper;
    @Autowired
    SysUserAgentMapper sysUserAgentMapper;
    @Autowired
    SysUserAdminMapper sysUserAdminMapper;
    @Autowired
    SysUserMerchantMapper userMerchantMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AccountTypeMapper accountTypeMapper;
    @Autowired
    OrderMainMapper orderMainMapper;

    @Test
    @Ignore
    public void run() {
        Api api = new Api();
        QueryWrapper<SysAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", "1");
        List<SysAddress> sysAddresses = sysAddressMapper.selectList(queryWrapper);
        sysAddresses.forEach(api::get);
    }

    @Test
    @Ignore
    public void run1() throws IOException, InvalidFormatException {
        File file = ResourceUtils.getFile("classpath:test.xls");
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String id = row.getCell(0).getStringCellValue();
            String c = row.getCell(14).getStringCellValue();
            String u = row.getCell(16).getStringCellValue();
            OrderMain orderMain = new OrderMain();
            orderMain.setId(id);
            orderMain.setCreateTime(LocalDateTime.parse(c));
            orderMain.setUpdateTime(LocalDateTime.parse(u));
            orderMainMapper.updateById(orderMain);
        }
    }

    @Test
    @Ignore
    @Rollback(value = false)
    public void user2Account() {
        agent2Account();
        admin2Account();
        merchant2Account();
    }

    private void agent2Account() {
        QueryWrapper<SysUserAgent> agentQueryWrapper = new QueryWrapper<>();
        agentQueryWrapper.lambda().eq(SysUserAgent::getStatus, 0);
        sysUserAgentMapper.selectList(agentQueryWrapper)
                .forEach(sysUserAgent -> {
                    QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("username", sysUserAgent.getAccount());
                    if (accountMapper.selectCount(queryWrapper) == 0) {
                        Account account = new Account();
                        account.setNickname(sysUserAgent.getName());
                        account.setOpenId(sysUserAgent.getOpenid());
                        account.setPassword(sysUserAgent.getPassword());
                        account.setSalt(sysUserAgent.getSalt());
                        account.setPhone(StringUtils.isEmpty(sysUserAgent.getPhone()) ? null : sysUserAgent.getPhone());
                        account.setUsername(sysUserAgent.getAccount());
                        accountMapper.insert(account);
                    }
                    AccountType accountType = new AccountType();
                    accountType.setMemberType(RoleConstants.AGENT_ROLE);
                    accountType.setAccountId(accountMapper.selectOne(queryWrapper).getId());
                    accountType.setMemberId(sysUserAgent.getId());
                    accountTypeMapper.insert(accountType);
                });
    }

    private void admin2Account() {
        QueryWrapper<SysUserAdmin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.lambda().eq(SysUserAdmin::getStatus, 0);
        sysUserAdminMapper.selectList(adminQueryWrapper)
                .forEach(sysUserAdmin -> {
                    QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("username", sysUserAdmin.getAccount());
                    if (accountMapper.selectCount(queryWrapper) == 0) {
                        Account account = new Account();
                        account.setNickname(sysUserAdmin.getName());
                        account.setPassword(sysUserAdmin.getPassword());
                        account.setSalt(sysUserAdmin.getSalt());
                        account.setPhone(StringUtils.isEmpty(sysUserAdmin.getPhone()) ? null : sysUserAdmin.getPhone());
                        account.setUsername(sysUserAdmin.getAccount());
                        accountMapper.insert(account);
                    }
                    AccountType accountType = new AccountType();
                    accountType.setMemberType(RoleConstants.ADMIN_ROLE);
                    accountType.setAccountId(accountMapper.selectOne(queryWrapper).getId());
                    accountType.setMemberId(sysUserAdmin.getId());
                    accountTypeMapper.insert(accountType);
                });
    }

    private void merchant2Account() {
        QueryWrapper<SysUserMerchant> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.lambda().eq(SysUserMerchant::getStatus, 0);
        userMerchantMapper.selectList(merchantQueryWrapper)
                .forEach(sysUserMerchant -> {
                    QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("username", sysUserMerchant.getAccount());
                    if (accountMapper.selectCount(queryWrapper) == 0) {
                        Account account = new Account();
                        account.setNickname(sysUserMerchant.getName());
                        account.setPassword(sysUserMerchant.getPassword());
                        account.setSalt(sysUserMerchant.getSalt());
                        account.setPhone(StringUtils.isEmpty(sysUserMerchant.getPhone()) ? null : sysUserMerchant.getPhone());
                        account.setUsername(sysUserMerchant.getAccount());
                        account.setOpenId(sysUserMerchant.getOpenid());
                        accountMapper.insert(account);
                    }
                    AccountType accountType = new AccountType();
                    accountType.setMemberType(RoleConstants.MERCHANT_ROLE);
                    accountType.setAccountId(accountMapper.selectOne(queryWrapper).getId());
                    accountType.setMemberId(sysUserMerchant.getId());
                    accountTypeMapper.insert(accountType);
                });
    }

    class Api {

        void get(SysAddress sysAddress) {
            JSONObject jsonObject = HttpClientUtilsTool.httpGet(buildUrl(sysAddress.getName()));
            if ("1".equals(jsonObject.getString("status"))) {
                jsonObject.getJSONArray("districts").forEach(address -> mapping((JSONObject) address));
            }
        }

        private void mapping(JSONObject jsonObject) {
            System.out.println(jsonObject);
            QueryWrapper<SysAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", jsonObject.getString("name"));
            if (sysAddressMapper.selectCount(queryWrapper) == 1) {
                SysAddress sysAddress = sysAddressMapper.selectOne(queryWrapper);
                if (sysAddress != null) {
                    sysAddress.setAdcode(jsonObject.getString("adcode"));
                    sysAddressMapper.updateById(sysAddress);
                }
            }
            jsonObject.getJSONArray("districts").forEach(address -> mapping((JSONObject) address));
        }

        private String buildUrl(String q) {
            String url = "https://restapi.amap.com/v3/config/district?";
            url += "key=0f4a8c29fa41877717f64c9d8948ba51";
            url += "&keywords=" + q;
            url += "&subdistrict=3";
            return url;
        }


    }

}
