package com.skplanet.storeplatform.sac.member.user.service;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.CommonCode;
import com.skplanet.storeplatform.sac.member.domain.shared.UserManagementItem;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserManagementItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 인터페이스
 *
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 * Updated on : 2016. 1. 14. Updated by : 정희원, SKP
 */

@Service
@Transactional(value = "transactionManagerForMember", readOnly = true)
public class UserExtraInfoServiceImpl implements UserExtraInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserExtraInfoServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

    @Autowired
    private UserMemberService memberService;

    @Autowired
    private UserManagementItemRepository managementItemRepository;

    @Override
    @Transactional(readOnly = false)
    public void modifyExtraInfo(final String userKey, final List<UserExtraInfo> reqInfos) {
        UserMember member = checkUser(userKey);
        if(member.isFromIdle())
            return;

        /* 입력받은 profileCode 정상인지 확인 */
        List<String> reqCodes = Lists.transform(reqInfos, new Function<UserExtraInfo, String>() {
            @Nullable
            @Override
            public String apply(UserExtraInfo input) {
                return input.getExtraProfile();
            }
        });

        checkProfileCodes(reqCodes);

        for (UserExtraInfo v : reqInfos) {
            UserManagementItem item = new UserManagementItem();
            item.setMember(member);
            item.setMangItemCd(v.getExtraProfile());
            item.setRegResultValue(v.getExtraProfileValue());

            managementItemRepository.save(item);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void modifyExtraInfo(String userKey, String itemId, String value) {
        modifyExtraInfo(userKey, Arrays.asList(new UserExtraInfo(itemId, value)));
    }

    /**
	 * 사용자 부가정보 리스트
     * @param userKey
     */
	@Override
    @Transactional
	public List<UserExtraInfo> findExtraInfo(String userKey) {

        checkUser(userKey);

        List<UserManagementItem> userMangItems = managementItemRepository.findByMemberUserKey(userKey);

		return Lists.transform(userMangItems, new Function<UserManagementItem, UserExtraInfo>() {
            @Override
            public UserExtraInfo apply(UserManagementItem input) {
                return input.convertToExtraInfo();
            }
        });
	}

    @Override
    @Transactional
    public String getExtraInfoValue(String userKey, String itemId) {

//        checkUser(userKey); // 없어도 무방하다

        UserManagementItem item = managementItemRepository.findByUserKeyAndItemCd(userKey, itemId);
        if(item == null)
            return null;

        return item.getRegResultValue();
    }

    @Override
    @Transactional(readOnly = false)
    public void removeExtraInfo(String userKey, List<String> infoKeyList) {

        UserMember member = checkUser(userKey);
        if(member.isFromIdle())
            return;

        checkProfileCodes(infoKeyList);

        List<UserManagementItem> mangItems = managementItemRepository.findByMemberUserKey(userKey);
        ImmutableMap<String, UserManagementItem> userMangMap = Maps.uniqueIndex(mangItems, new Function<UserManagementItem, String>() {
            @Override
            public String apply(UserManagementItem input) {
                return input.getMangItemCd();
            }
        });

        for (String key : infoKeyList) {
            UserManagementItem mangItem = userMangMap.get(key);
            if(mangItem == null)
                throw new StorePlatformException("SC_MEM_9983");

            managementItemRepository.remove(mangItem);
        }
    }

    /**
     * 회원 정보 조회
     * @param userKey
     */
	private UserMember checkUser(String userKey) {
        UserMember member = memberService.findByUserKeyAndTransitRepo(userKey);
        if (member == null)
            throw new StorePlatformException("SC_MEM_9995");

        return member;
    }

    /**
     * 입력 값이 유효한 코드인지 확인한다.
     * @param profileCodes
     */
	private void checkProfileCodes(List<String> profileCodes) {
		List<CommonCode> codes = this.mcc.getCommonCode(MemberConstants.USER_EXTRA_GROP_CD);

        Set<String> codeSet = Sets.newHashSet(Lists.transform(codes, new Function<CommonCode, String>() {
            @Override
            public String apply(CommonCode input) {
                return input.getCdId();
            }
        }));

        for (String code : profileCodes) {
            if(!codeSet.contains(code))
                throw new StorePlatformException("SAC_MEM_1403");
        }
	}
}
