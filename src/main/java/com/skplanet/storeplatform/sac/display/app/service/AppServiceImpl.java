package com.skplanet.storeplatform.sac.display.app.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetail;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSource;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSourceReq;
import com.skplanet.storeplatform.sac.display.app.vo.UpdateHistory;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import freemarker.template.SimpleDate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 앱 상품 상세조회
 * <p/>
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {

	private static Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

	@Override
	public AppDetailRes getAppDetail(AppDetailReq request) {

        AppDetail appDetail = commonDAO.queryForObject("AppDetail.getAppDetail", request, AppDetail.class);
        if(appDetail == null)
            throw new StorePlatformException("SAC_DSP_9999");
        AppDetailRes res = new AppDetailRes();

        // Product Basic info
        Product product = new Product();
        product.setIdentifier(new Identifier("episode", request.getEpisodeId()));
        product.setPacketFee(appDetail.getProdGbn());

        product.setTitle(new Title(appDetail.getWapProdNm()));
        Price price = new Price();
        price.setFixedPrice(appDetail.getProdAmt());
        product.setPrice(price);

        // Menu
        List<MenuItem> menuList = commonService.getMenuItemList(request.getEpisodeId(), request.getLangCd());
        product.setMenuList(new ArrayList<Menu>());
        for (MenuItem mi : menuList) {
            Menu menu = new Menu();
            menu.setId(mi.getMenuId());
            menu.setName(mi.getMenuNm());
            if(mi.isInfrMenu())
                menu.setType("topClass");

            product.getMenuList().add(menu);
        }

        // Source
        // TODO thumbnail, screenshot
        List<ImageSource> imageSourceList = commonDAO.queryForList("AppDetail.getSourceList", new ImageSourceReq(request.getEpisodeId(), "DP000111", request.getLangCd()), ImageSource.class);
        List<Source> sourceList = new ArrayList<Source>();
        for (ImageSource imgSrc : imageSourceList) {
            Source source = new Source();
            source.setMediaType(DisplayCommonUtil.getMimeType(imgSrc.getFileNm()));
            source.setType("screenshot");
            source.setUrl(imgSrc.getFilePath());

            sourceList.add(source);
        }
        product.setSourceList(sourceList);

        Accrual accrual = new Accrual();
        accrual.setVoterCount(appDetail.getPaticpersCnt());
        accrual.setDownloadCount(appDetail.getDwldCnt());
        accrual.setScore(appDetail.getAvgEvluScore());
        product.setAccrual(accrual);

        Rights rights = new Rights();
        rights.setGrade(appDetail.getProdGrd());
        product.setRights(rights);

        // App
        App app = new App();
        product.setApp(app);
        app.setAid(appDetail.getAid());
        app.setSupportedOs("Android " + appDetail.getVmVer());
        app.setPackageName(appDetail.getApkPkgNm());
        app.setVersionCode(appDetail.getApkVer());
        app.setVersion(appDetail.getApkVerNm());
        app.setSize(appDetail.getFileSize());

        // AppDebug
        AppDebug debug = new AppDebug();
        debug.setPackageName(appDetail.getApkPkgNm());  // TODO check
        debug.setVersionCode(appDetail.getApkVer());    // TODO check
        app.setAppDebug(debug);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

        // Update History
        History history = new History();
        List<UpdateHistory> updateHistoryList = commonDAO.queryForList("AppDetail.getUpdateHistoryList", request, UpdateHistory.class);
        List<Update> updateList = new ArrayList<Update>();
        for (UpdateHistory uh : updateHistoryList) {
            Update update = new Update();
            Date date = new Date();
            date.setType("date/reg");
            date.setText(sdf.format(uh.getProdUpdDt()));

            update.setUpdateExplain(uh.getUpdtText());
            update.setDate(date);

            updateList.add(update);
        }
        app.setHistory(history);
        history.setUpdate(updateList);

        // Distributor
        Distributor distributor = new Distributor();
        distributor.setIdentifier(appDetail.getSellerMbrNo());
        distributor.setName(appDetail.getExpoSellerNm()); // TODO name or nickname
        distributor.setTel(appDetail.getExpoSellerTelno());
        distributor.setEmail(appDetail.getExpoSellerEmail());
        product.setDistributor(distributor);

        res.setProduct(product);
        return res;
    }

}
