
package com.example.demo.wxTemplateMsg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 */
public class ShOrders {

	private static final long serialVersionUID = 1L;
	@WxEventMsgPram("订单号")
	private String sn;			// 订单号
	@WxEventMsgPram("店铺名称")
	private String branchName;   //店铺名称
	private String branchAddress;  //店铺地址
	private String status;	   // 状态   //deprecated 标记过时的订单  aheadRepaymentAll  提前结清
	@WxEventMsgPram("订单金额")
	private BigDecimal amount;		   // 订单金额
	@WxEventMsgPram("首付")
	private BigDecimal firstAmount;	   // 首付
	private int periods;		   	   // 期数
	@WxEventMsgPram("每期金额")
	private BigDecimal periodsAmount;  // 每期金额

	private FqMember borrower;		   // 借款人
	private String product;		       // 商品
	private Date lendDate;		       // 出借时间
	@WxEventMsgPram("商品金额")
	private BigDecimal productAmount;  // 商品金额
	private String memberRateConfig;   // 配置的利率ID
	private Date   orderDate;          // 确认办单时间
	private BigDecimal rebateAmount; //返利金额
	private BigDecimal frozenMarginAmount; //冻结的返利金额
	private int riskOver = 0;
	private String confirmImage; //确认单图片
    private BigDecimal moneyProportion;//打款比例(在店铺没有定义比列的订单进行自定义比例)
    private BigDecimal divisor;//首次打款比例
    private BigDecimal dividend;//总共需打款比例
	private int greenTong;//绿通（绿通不进行打款，即使商户定义比例也不打款）
	private BigDecimal amountOfMoney;//打款金额（数据库不加此字段）
	private String sellPhone;//销售员电话
	private String sellName;//销售员电话（查询使用，不加入数据库）
	private int paybackPeriodsAll;//回款期数--->默认回款期数计算方式（分期期数/2）
	private BigDecimal remitRestrict;//打款限制（打款上线）
	private int douDi;//是否兜底（1兜底，0不兜底）
	private BigDecimal douDiBiLi;//兜底比例
	private int subventionUser;//美丽援助金客户(0不是，1是)

	private String oldOrderSn;//保存原订单的sn

	private int privilegedPackage;//特权包0未购买，1购买
	private Date chargeBackDate;//退单时间
	private Date operationTime;//手术时间


	private Date beginDate;	// 开始查询日期
	private Date endDate;	// 结束查询日期
	private String daiPhone;	// 代理查询
	@WxEventMsgPram("当前期数")
	private int period;//当前期数
	private Date lasterRepaymentDate;//最后一期还款时间
	private int daili = 0;
	private Date orderEndDate;//还款截止日期

	FqMember fqMember;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}

	public BigDecimal getPeriodsAmount() {
		return periodsAmount;
	}

	public void setPeriodsAmount(BigDecimal periodsAmount) {
		this.periodsAmount = periodsAmount;
	}

	public FqMember getBorrower() {
		return borrower;
	}

	public void setBorrower(FqMember borrower) {
		this.borrower = borrower;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public String getMemberRateConfig() {
		return memberRateConfig;
	}

	public void setMemberRateConfig(String memberRateConfig) {
		this.memberRateConfig = memberRateConfig;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getFrozenMarginAmount() {
		return frozenMarginAmount;
	}

	public void setFrozenMarginAmount(BigDecimal frozenMarginAmount) {
		this.frozenMarginAmount = frozenMarginAmount;
	}

	public int getRiskOver() {
		return riskOver;
	}

	public void setRiskOver(int riskOver) {
		this.riskOver = riskOver;
	}

	public String getConfirmImage() {
		return confirmImage;
	}

	public void setConfirmImage(String confirmImage) {
		this.confirmImage = confirmImage;
	}

	public BigDecimal getMoneyProportion() {
		return moneyProportion;
	}

	public void setMoneyProportion(BigDecimal moneyProportion) {
		this.moneyProportion = moneyProportion;
	}

	public BigDecimal getDivisor() {
		return divisor;
	}

	public void setDivisor(BigDecimal divisor) {
		this.divisor = divisor;
	}

	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}

	public int getGreenTong() {
		return greenTong;
	}

	public void setGreenTong(int greenTong) {
		this.greenTong = greenTong;
	}

	public BigDecimal getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setAmountOfMoney(BigDecimal amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	public String getSellPhone() {
		return sellPhone;
	}

	public void setSellPhone(String sellPhone) {
		this.sellPhone = sellPhone;
	}

	public String getSellName() {
		return sellName;
	}

	public void setSellName(String sellName) {
		this.sellName = sellName;
	}

	public int getPaybackPeriodsAll() {
		return paybackPeriodsAll;
	}

	public void setPaybackPeriodsAll(int paybackPeriodsAll) {
		this.paybackPeriodsAll = paybackPeriodsAll;
	}

	public BigDecimal getRemitRestrict() {
		return remitRestrict;
	}

	public void setRemitRestrict(BigDecimal remitRestrict) {
		this.remitRestrict = remitRestrict;
	}

	public int getDouDi() {
		return douDi;
	}

	public void setDouDi(int douDi) {
		this.douDi = douDi;
	}

	public BigDecimal getDouDiBiLi() {
		return douDiBiLi;
	}

	public void setDouDiBiLi(BigDecimal douDiBiLi) {
		this.douDiBiLi = douDiBiLi;
	}

	public int getSubventionUser() {
		return subventionUser;
	}

	public void setSubventionUser(int subventionUser) {
		this.subventionUser = subventionUser;
	}

	public String getOldOrderSn() {
		return oldOrderSn;
	}

	public void setOldOrderSn(String oldOrderSn) {
		this.oldOrderSn = oldOrderSn;
	}

	public int getPrivilegedPackage() {
		return privilegedPackage;
	}

	public void setPrivilegedPackage(int privilegedPackage) {
		this.privilegedPackage = privilegedPackage;
	}

	public Date getChargeBackDate() {
		return chargeBackDate;
	}

	public void setChargeBackDate(Date chargeBackDate) {
		this.chargeBackDate = chargeBackDate;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDaiPhone() {
		return daiPhone;
	}

	public void setDaiPhone(String daiPhone) {
		this.daiPhone = daiPhone;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Date getLasterRepaymentDate() {
		return lasterRepaymentDate;
	}

	public void setLasterRepaymentDate(Date lasterRepaymentDate) {
		this.lasterRepaymentDate = lasterRepaymentDate;
	}

	public int getDaili() {
		return daili;
	}

	public void setDaili(int daili) {
		this.daili = daili;
	}

	public Date getOrderEndDate() {
		return orderEndDate;
	}

	public void setOrderEndDate(Date orderEndDate) {
		this.orderEndDate = orderEndDate;
	}

	public FqMember getFqMember() {
		return fqMember;
	}

	public void setFqMember(FqMember fqMember) {
		this.fqMember = fqMember;
	}
}