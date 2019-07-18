package com.lyzb.jbx.api;

import com.lyzb.jbx.model.dynamic.RequestRemoveDynamic;
import com.lyzb.jbx.model.me.AddCircleModel;
import com.lyzb.jbx.model.me.AgreedCircleModel;
import com.lyzb.jbx.model.me.CardComdModel;
import com.lyzb.jbx.model.me.CardFileDeModel;
import com.lyzb.jbx.model.me.CardImgTextModel;
import com.lyzb.jbx.model.me.CardTabHideBody;
import com.lyzb.jbx.model.me.CardUserInfoModel;
import com.lyzb.jbx.model.me.CircleOpeModel;
import com.lyzb.jbx.model.me.CircleRemModel;
import com.lyzb.jbx.model.me.CreateCompanyBody;
import com.lyzb.jbx.model.me.DoFocusModel;
import com.lyzb.jbx.model.me.DoLikeModel;
import com.lyzb.jbx.model.me.JoinCompanyBody;
import com.lyzb.jbx.model.me.SaveTemplateBody;
import com.lyzb.jbx.model.me.SetComdModel;
import com.lyzb.jbx.model.me.UpdCircleModel;
import com.lyzb.jbx.model.me.customerManage.CustomerInfoModel;
import com.lyzb.jbx.model.params.AddOrganBody;
import com.lyzb.jbx.model.params.AddStaffBody;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.model.params.ApplyOpenBody;
import com.lyzb.jbx.model.params.AuditMembersBody;
import com.lyzb.jbx.model.params.BodyId;
import com.lyzb.jbx.model.params.ChangeAdminBody;
import com.lyzb.jbx.model.params.CompanyAccountBody;
import com.lyzb.jbx.model.params.CustomerAddTrackBody;
import com.lyzb.jbx.model.params.DeleteCompanyBody;
import com.lyzb.jbx.model.params.IdBody;
import com.lyzb.jbx.model.params.RemoveMembersBody;
import com.lyzb.jbx.model.params.SaveCardModelBody;
import com.lyzb.jbx.model.params.SetCompanyTabBody;
import com.lyzb.jbx.model.params.UpdateModelBody;
import com.lyzb.jbx.model.params.UserPrivacyInfoBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author wyx
 * @role 我的模块-相关api
 * @time 2019 2019/3/11 13:28
 */

public interface IMeApi {

    /****
     * 获取行业列表
     */
    @GET("lbs/gs/user/selectGsProfessionList")
    Observable<Response<String>> getIndustryList(@HeaderMap Map<String, String> headerParams);

    /****
     * 获取我的粉丝/关注 列表
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @param type  1:粉丝  2:关注
     * @return
     */
    @GET("lbs/gs/topic/selectMyRelatioList")
    Observable<Response<String>> getFansList(@HeaderMap Map<String, String> headerParams,
                                             @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize, @Query("type") int type, @Query("userId") String userId,
                                             @Query("searchName") String searchName);

    /****
     * 我的收藏 列表
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gs/topic/selectMyTopicFavour")
    Observable<Response<String>> getCollList(@HeaderMap Map<String, String> headerParams,
                                             @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize);

    /****
     * 获取 我的名片
     * 不需要传参数
     */
    @GET("lbs/gs/user/getMyGaUserExtVoById")
    Observable<Response<String>> getCardData(@HeaderMap Map<String, String> headerParams);

    /****
     * 获取 TA的名片数据
     * @param headerParams
     * @param params
     * @return
     */
    @GET("lbs/gs/user/getGaUserExtVoById")
    Observable<Response<String>> getOtherCardData(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    /****
     * 个人信息编辑or其他信息编辑
     * @param headerParams
     * @param model 信息model
     * @return
     */
    @POST("lbs/gs/user/saveGaUserExt")
    Observable<Response<String>> saveOtherInfo(@HeaderMap Map<String, String> headerParams, @Body CardUserInfoModel model);

    /****
     * 名片-修改,添加 个人简介信息 个人荣誉 个人需求  个人提供
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gs/user/updateAttribute")
    Observable<Response<String>> saveCardInfo(@HeaderMap Map<String, String> headerParams, @Body CardImgTextModel model);


    /****
     * 名片-修改,添加 个人简介信息 个人荣誉 个人需求  个人提供  模块
     * @param headerParams
     * @param model
     * @return 修改单个模板
     */
    @POST("lbs/gsParagraph/addOrUpdate")
    Observable<Response<String>> updateCardModel(@HeaderMap Map<String, String> headerParams, @Body UpdateModelBody model);

    /****
     * 名片-,添加 个人简介信息 个人荣誉 个人需求  个人提供  模块
     * @param headerParams
     * @param model
     * @return 添加多个模板
     */
    @POST("lbs/gsModular/addOrUpdate")
    Observable<Response<String>> saveCardModel(@HeaderMap Map<String, String> headerParams, @Body SaveCardModelBody model);


    /****
     * 名片-,删除个人简介信息 个人荣誉 个人需求  个人提供  模块
     * @param headerParams
     * @param bodyId
     * @return 删除模板内容
     */
    @POST("lbs/gsParagraph/delete")
    Observable<Response<String>> deleteCardModelContent(@HeaderMap Map<String, String> headerParams, @Body BodyId bodyId);


    /****
     * 名片-,删除个人简介信息 个人荣誉 个人需求  个人提供  模块
     * @param headerParams
     * @return 删除模板
     */
    @POST("lbs/gsModular/delete")
    Observable<Response<String>> deleteCardModel(@HeaderMap Map<String, String> headerParams, @Body BodyId bodyId);

    /****
     * 名片-删除 文件(视频 图片)
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gs/user/delGsUserFile")
    Observable<Response<String>> deleteFile(@HeaderMap Map<String, String> headerParams, @Body CardFileDeModel model);

    /*****
     * 访问我的人 记录统计
     * @param headerParams
     * @return
     */
    @GET("lbs/gs/user/getUserTrack")
    Observable<Response<String>> getAccessNumber(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    /****
     * 获取 指定用户的具体访问记录
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return
     */
    @GET("lbs/gs/user/selectMyAccessUserVo")
    Observable<Response<String>> getAcsList(@HeaderMap Map<String, String> headerParams,
                                            @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize,
                                            @Query("tagUserId") String userId, @Query("shareView") int shareView,
                                            @Query("viewType") int viewType, @Query("dayNum") String dayNum);


    //获取xx用户的具体热文记录
    @GET("lbs/gs/user/selectHotUserDetailList")
    Observable<Response<String>> getAcsHotList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    /*****
     * 访问我的人 列表 data
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gs/user/selectMyViewRecordVoList")
    Observable<Response<String>> getAccessList(@HeaderMap Map<String, String> headerParams, @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize);

    /****
     * 访问我的人-指定Id人的访问记录
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @param tagId
     * @return
     */
    @GET("/lbs/gs/user/selectMyAccessUserVo")
    Observable<Response<String>> getAccessById(@HeaderMap Map<String, String> headerParams,
                                               @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize, @Query("tagUserId") int tagId);

    /****
     * 我的圈子 列表
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gsGroup/queryUserByAllGroup")
    Observable<Response<String>> getCircleList(@HeaderMap Map<String, String> headerParams,
                                               @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize);

    /****
     * 创建 圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/addOrUpdateGroup")
    Observable<Response<String>> createCircle(@HeaderMap Map<String, String> headerParams, @Body AddCircleModel model);

    /****
     * 修改 圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/addOrUpdateGroup")
    Observable<Response<String>> uptCircle(@HeaderMap Map<String, String> headerParams, @Body UpdCircleModel model);

    /****
     * 圈子详情
     * @param headerParams
     * @param id
     * @return
     */
    @GET("lbs/gsGroup/detail")
    Observable<Response<String>> getCircleData(@HeaderMap Map<String, String> headerParams, @Query("id") String id);

    /****
     * 申请 加入该圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/applyGroup")
    Observable<Response<String>> applyCir(@HeaderMap Map<String, String> headerParams, @Body ApplyCircleBody model);

    /****
     * 我的企业 -设置默认企业
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gs/distributor/doSelectCompany")
    Observable<Response<String>> setComd(@HeaderMap Map<String, String> headerParams, @Body SetComdModel model);


    /****
     * 创建or修改企业信息
     * @param headerParams
     * @body
     * @return
     */
    @POST("lbs/gs/distributor/addOrUptCompany")
    Observable<Response<String>> updateComd(@HeaderMap Map<String, String> headerParams, @Body CardComdModel model);

    /****
     * 获取 企业详情
     * @param headerParams
     * @param Id
     * @return
     */
    @GET("lbs/gs/distributor/getCompanyDetail")
    Observable<Response<String>> getComdInfo(@HeaderMap Map<String, String> headerParams, @Query("companyId") String Id);


    /****
     * 获取 企业模板详情详情
     * @param headerParams
     * @param Id
     * @return
     */
    @GET("lbs/gs/org/getOrgWebsite")
    Observable<Response<String>> getComdModelInfo(@HeaderMap Map<String, String> headerParams, @Query("orgId") String Id);

    //名片的关注和取消
    @POST("lbs/gs/user/saveUsersRelation")
    Observable<Response<String>> onCardFollow(@HeaderMap Map<String, String> headerParams, @Body Map<String, Object> params);

    //申请经销商或运营伙伴
    @POST("lbs/gs/distributor/sharePlatRegister")
    Observable<Response<String>> onApplyCompany(@HeaderMap Map<String, String> headerParams, @Body ApplyOpenBody params);

    /****
     * 获取圈子的全部成员
     * @param headerParams
     * @param groupId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gsGroup/queryGroupMembers")
    Observable<Response<String>> getCirMebList(@HeaderMap Map<String, String> headerParams,
                                               @Query("groupId") String groupId,
                                               @Query("pageNum") int pageNumber,
                                               @Query("pageSize") int pageSize,
                                               @Query("userName") String userName);

    /****
     * 退出圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/introductionGroupMembers")
    Observable<Response<String>> onExitCircle(@HeaderMap Map<String, String> headerParams, @Body CircleOpeModel model);

    /****
     * 解散圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/deleteGroup")
    Observable<Response<String>> onDissCircle(@HeaderMap Map<String, String> headerParams, @Body CircleOpeModel model);


    /****
     * 移除圈子中的成员
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/deleteGroupMembers")
    Observable<Response<String>> onRemoveCircle(@HeaderMap Map<String, String> headerParams, @Body CircleRemModel model);

    /****
     * 获取 圈子成员 申请列表
     * @param headerParams
     * @param groupId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gsGroup/applyGroupList")
    Observable<Response<String>> getApplyList(@HeaderMap Map<String, String> headerParams,
                                              @Query("groupId") String groupId,
                                              @Query("pageNum") int pageNumber,
                                              @Query("pageSize") int pageSize,
                                              @Query("pass") int pass);

    /****
     * 同意XXX进入圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/applyGroup")
    Observable<Response<String>> onAgreedCir(@HeaderMap Map<String, String> headerParams, @Body AgreedCircleModel model);

    /****
     * 拒绝XXX进入圈子
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gsGroup/refuseGroup")
    Observable<Response<String>> onRefuseCir(@HeaderMap Map<String, String> headerParams, @Body AgreedCircleModel model);

    /*****
     * TA的名片 点赞/取消点赞
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gs/user/saveGsOperRecord")
    Observable<Response<String>> doLike(@HeaderMap Map<String, String> headerParams, @Body DoLikeModel model);

    /****
     * TA的名片 关注/取消关注
     * @param headerParams
     * @param model
     * @return
     */
    @POST("lbs/gs/user/saveUsersRelation")
    Observable<Response<String>> doFocus(@HeaderMap Map<String, String> headerParams, @Body DoFocusModel model);


    /****
     * 名片-我的动态列表
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gs/topic/selectTopicInfoList")
    Observable<Response<String>> getMeDycList(@HeaderMap Map<String, String> headerParams,
                                              @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize);

    /****
     * 名片-别人的动态列表
     * @param headerParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gs/topic/selectTopicInfoListByUserId")
    Observable<Response<String>> getOthDycList(@HeaderMap Map<String, String> headerParams,
                                               @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize, @Query("userId") String userId);

    /****
     * 圈子-该圈子的动态详情
     * @param headerParams
     * @param cirId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("lbs/gsGroup/topicList")
    Observable<Response<String>> getCirDync(@HeaderMap Map<String, String> headerParams,
                                            @Query("groupId") String cirId, @Query("pageNum") int pageNumber, @Query("pageSize") int pageSize);

    //我发布的--回复列表
    @GET("lbs/gs/topic/selectMyTopicComment")
    Observable<Response<String>> getMeReleaseList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);


    /*****
     * 删除动态
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/topic/delPublish")
    Observable<Response<String>> removeDynamic(@HeaderMap Map<String, String> headerParams, @Body RequestRemoveDynamic body);

    @GET("lbs/gs/user/selectRoleMembership")
    Observable<Response<String>> getCirStatus(@HeaderMap Map<String, String> headerParams);

    /*****
     * 加入企业
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/distributor/doJoinCompany")
    Observable<Response<String>> joinCompany(@HeaderMap Map<String, String> headerParams, @Body JoinCompanyBody body);

    /**
     * 获取访问名片记录
     *
     * @param headerParams
     * @param map
     * @return
     */
    @GET("lbs/gs/user/selectMyViewList")
    Observable<Response<String>> getAccessRecordList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> map);

    /**
     * 创建企业
     *
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/distributor/addOrUptCompany")
    Observable<Response<String>> createCompany(@HeaderMap Map<String, String> headerParams, @Body CreateCompanyBody body);

    //设置用户资料是否公开
    @POST("lbs/gs/user/updateGaUserExtShowInfo")
    Observable<Response<String>> setPrivacyswitch(@HeaderMap Map<String, String> headerParams, @Body UserPrivacyInfoBody body);

    @GET("lbs/gs/user/selectCurrentView")
    Observable<Response<String>> getAccessNumberDetail(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    /**
     * 我的企业-企业信息-申请列表数量/申请列表
     *
     * @param headerParams
     * @return
     */
    @GET("lbs/gs/distributor/getApplyJoinAuditList")
    Observable<Response<String>> getApplyJoinAuditList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);

    /**
     * 我的企业-企业信息
     *
     * @param headerParams
     * @return
     */
    @GET("lbs/gs/distributor/getCompanyInfo")
    Observable<Response<String>> getCompanyDetail(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);


    /**
     * 我的企业-审核申请成员
     *
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/distributor/doAuditMembers")
    Observable<Response<String>> doAuditMembers(@HeaderMap Map<String, String> headerParams, @Body AuditMembersBody body);

    /**
     * 我的企业-企业信息-查询企业成员列表
     *
     * @param headerParams
     * @param map
     * @return
     */
    @GET("lbs/gs/distributor/getCompanyMembers")
    Observable<Response<String>> getCompanyMembers(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);

    /**
     * 我的企业-移除企业成员/退出企业s
     *
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/distributor/doRemoveMembers")
    Observable<Response<String>> doRemoveMembers(@HeaderMap Map<String, String> headerParams, @Body RemoveMembersBody body);


    /**
     * 我的企业-企业信息-查询企业成员列表
     *
     * @param headerParams
     * @param map
     * @return
     */
    @GET("lbs/gs/distributor/getCompanyAccounts")
    Observable<Response<String>> getCompanyAccounts(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);


    /**
     * 我的企业-团购企业-企业账号详情
     *
     * @param headerParams
     * @param map
     * @return
     */
    @GET("lbs/gs/distributor/getCompanyAccountDetail")
    Observable<Response<String>> getCompanyAccountDetail(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);

    /**
     * 我的企业-团购企业-编辑企业账号
     *
     * @param headerParams
     * @param body
     * @return
     */
    @POST("lbs/gs/distributor/uptCompanyAccount")
    Observable<Response<String>> uptCompanyAccount(@HeaderMap Map<String, String> headerParams, @Body CompanyAccountBody body);

    //获取 我的-客户追踪-分享数据分析
    @GET("lbs/gs/user/selectMyShareNumVo")
    Observable<Response<String>> getAccessShareDetail(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //获取 我的-客户追踪-引流新用户
    @GET("lbs/gsUserBelong/selectByPageInfo")
    Observable<Response<String>> getAccessNewAccount(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //获取 我的-客户追踪-名片商城交易
    @GET("lbs/gs/home/getOrdersByCard")
    Observable<Response<String>> getAccessShop(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //获取 我的-我的足迹
    @GET("lbs/gs/user/selectMyAccessRecord")
    Observable<Response<String>> getAccessMyRecord(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //清空我的足迹
    @GET("lbs/gs/user/clearMyAccessRecord")
    Observable<Response<String>> clearAccessMyRecord(@HeaderMap Map<String, String> headerParams);

    //获取 我的-XX的访问人列表记录
    @GET("lbs/gs/user/selectMyViewRecordVoList")
    Observable<Response<String>> getAccessAccountList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //获取我的--XX的热文数据列表
    @GET("lbs/gs/user/selectHotUserList")
    Observable<Response<String>> getAccessHotList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, Object> params);

    //名片下的-名片模板列表
    @GET("lbs/gs/user/selectGsTemplateByUser")
    Observable<Response<String>> getTemplateList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> params);

    /**
     * 获取客户管理列表
     */
    @GET("lbs/gsCustomers/selectByPageInfo")
    Observable<Response<String>> getCustomerManage(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 客户管理-详细资料
     */
    @GET("lbs/gsCustomers/selectById")
    Observable<Response<String>> getCustomerUserInfo(@HeaderMap Map<String, String> heards, @Query("id") String id);

    /**
     * 客户管理-添加跟进
     */
    @POST("lbs/gsCustomers/addOrUpdateCustomersFollow")
    Observable<Response<String>> addTrack(@HeaderMap Map<String, String> heards, @Body CustomerAddTrackBody body);

    /**
     * 客户管理-删除客户
     */
    @POST("lbs/gsCustomers/deleteCustomers")
    Observable<Response<String>> deleteCustomer(@HeaderMap Map<String, String> heards, @Body IdBody body);

    /**
     * 客户管理-跟进记录
     */
    @GET("lbs/gsCustomers/selectFollowPageInfo")
    Observable<Response<String>> getCustomerTrackRecord(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 客户管理-设为意向客户（修改客户备注）
     */
    @POST("lbs/gsCustomers/addOrUpdateCustomers")
    Observable<Response<String>> addCustomer(@HeaderMap Map<String, String> heards, @Body CustomerInfoModel body);

    /**
     * 客户管理-企业
     */
    @GET("lbs/gs/distributor/getCompanyCustomer")
    Observable<Response<String>> getCustomerManageCompany(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 获取分享语
     */
    @GET("lbs/gs/user/selectShareWord")
    Observable<Response<String>> getShareWord(@HeaderMap Map<String, String> heards);

    /**
     * 获取企业圈子tab
     */
    @GET("lbs/gs/user/selectGsCardFunction")
    Observable<Response<String>> getCompanyCircleTab(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 保存企业圈子tab
     */
    @POST("lbs/gs/user/saveGsCardFunctionSet")
    Observable<Response<String>> saveCompanyCircleTab(@HeaderMap Map<String, String> heards, @Body SetCompanyTabBody body);

    /**
     * 企业动态
     */
    @GET("lbs/gsGroup/newsPageList")
    Observable<Response<String>> getCompanyDynamic(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);


    /**
     * 查询名片tab栏目
     */
    @GET("lbs/gs/user/selectGsCardFunction")
    Observable<Response<String>> getCardTabList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 保存名片tab栏目
     */
    @POST("lbs/gs/user/saveGsCardFunctionSet")
    Observable<Response<String>> saveCardTabShowHide(@HeaderMap Map<String, String> heards, @Body CardTabHideBody body);

    /**
     * 成员动态
     */
    @GET("lbs/gsGroup/companyMemberDynamic")
    Observable<Response<String>> getMemberDynamic(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);

    /**
     * 生成二维码
     */
    @GET("lbs/wxminiapp/user/getminiqrQrUrl")
    Observable<Response<String>> getMiniqrQrUrl(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> param);


    /**
     * 启用模板
     */
    @POST("lbs/gs/goods/startUsingGsTemplate")
    Observable<Response<String>> saveCardTemplate(@HeaderMap Map<String, String> heards, @Body SaveTemplateBody body);

    /**
     * 企业列表
     */
    @GET("lbs/gs/org/getCompanyOrgList")
    Observable<Response<String>> getMyCompanyList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Integer> param);

    /**
     * 查询企业列表
     *
     * @param headerParams
     * @return
     */
    @GET("lbs/gs/user/queryDistributorByName")
    Observable<Response<String>> getCompanyList(@HeaderMap Map<String, String> headerParams, @QueryMap Map<String, String> map);

    /**
     * 编辑机构获取机构信息
     */
    @GET("lbs/gs/org/getCompanyOrgInfo")
    Observable<Response<String>> getOrganInfo(@HeaderMap Map<String, String> heards, @Query("companyId") String companyId);

    /**
     * 获取选择上级机构-列表
     */
    @GET("lbs/gs/org/getUptSelectOrgList")
    Observable<Response<String>> getOrganList(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> map);

    /**
     * 机构详情
     */
    @GET("lbs/gs/org/getCompanyOrgDetail")
    Observable<Response<String>> getOrganDetail(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> map);

    /**
     * 添加、修改、删除机构
     */
    @POST("lbs/gs/org/addOrUptOrg")
    Observable<Response<String>> addOrgan(@HeaderMap Map<String, String> heards, @Body AddOrganBody body);

    /**
     * 查询能否删除机构
     */
    @GET("lbs/gs/org/getCanDelOrg")
    Observable<Response<String>> canDeleteOrgan(@HeaderMap Map<String, String> heards, @Query("orgId") String orgId);

    /**
     * 查询企业帐号前缀
     */
    @GET("lbs/gs/org/getAccountPrefix")
    Observable<Response<String>> getAccountPrefix(@HeaderMap Map<String, String> heards, @Query("orgId") String orgId);

    /**
     * 获取企业管理员或全部成员
     */
    @GET("lbs/gs/org/getOrgManagersOrMember")
    Observable<Response<String>> getOrganMember(@HeaderMap Map<String, String> heards, @QueryMap Map<String, Object> map);

    /**
     * 获取企业管理员或全部成员
     */
    @POST("lbs/gs/org/doOptOrgManager")
    Observable<Response<String>> changeAdmin(@HeaderMap Map<String, String> heards, @Body ChangeAdminBody body);

    /**
     * 查询员工详情
     */
    @GET("lbs/gs/org/getOrgMemberInfo")
    Observable<Response<String>> getStaffDetail(@HeaderMap Map<String, String> herards, @QueryMap Map<String, String> map);

    /**
     * 获取企业管理员或全部成员
     */
    @POST("lbs/gs/org/doAddOrUptStaff")
    Observable<Response<String>> addStaff(@HeaderMap Map<String, String> heards, @Body AddStaffBody body);

    /**
     * 查询申请用户信息
     */
    @GET("lbs/gs/org/getStaffDetail")
    Observable<Response<String>> getUserDetail(@HeaderMap Map<String, String> herards, @Query("userId") String userId);

    /**
     * 添加、编辑员工时校验手机号
     */
    @GET("lbs/gs/org/doCheckMobile")
    Observable<Response<String>> checkPhone(@HeaderMap Map<String, String> herards, @Query("mobile") String userId);

    //获取个人中心模版权限
    @GET("lbs/gs/distributor/getRoleListByUser")
    Observable<Response<String>> getMeFunctionList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> map);

    /**
     * 解散企业
     */
    @POST("lbs/gs/org/doDismissDis")
    Observable<Response<String>> deleteCompany(@HeaderMap Map<String, String> headers, @Body DeleteCompanyBody body);
}
