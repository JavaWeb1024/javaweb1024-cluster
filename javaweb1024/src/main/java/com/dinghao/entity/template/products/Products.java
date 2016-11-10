/*
* @ClassName:Products.java
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author: 
*-----------Zihan--www.javaweb1024.com 版权所有------------
* @date 2016-10-13
*/
package com.dinghao.entity.template.products;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.dinghao.entity.BaseEntity;

public class Products extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * .产品类别,1酒水,2 文物
     */
    private Integer productCategory;

    /**
     * .产品名称
     */
    private String productName;

    /**
     * .产品编号
     */
    private String productNumber;

    /**
     * .产品容量
     */
    private String productCapacity;

    /**
     * .产品材质
     */
    private String productMaterial;

    /**
     * .生产产地
     */
    private String productHome;

    /**
     * .是否推荐产品
     */
    private Boolean perfectProduct;

    /**
     * .生产企业
     */
    private String productCompany;

    /**
     * .生产日期
     */
    private Date productDate;

    /**
     * .创建人员ID
     */
    private Long createBy;

    /**
     * .备用(数字型)
     */
    private Integer remarks;

    /**
     * .商品链接地址
     */
    private String remarks1;

    /**
     * .备用2
     */
    private String remarks2;

    /**
     * .商户主键
     */
    private Long tempAdminId;

    
    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = StringUtils.isBlank(productName)? null : productName.trim();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = StringUtils.isBlank(productNumber)? null : productNumber.trim();
    }

    public String getProductCapacity() {
        return productCapacity;
    }

    public void setProductCapacity(String productCapacity) {
        this.productCapacity = StringUtils.isBlank(productCapacity)? null : productCapacity.trim();
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = StringUtils.isBlank(productMaterial)? null : productMaterial.trim();
    }

    public String getProductHome() {
        return productHome;
    }

    public void setProductHome(String productHome) {
        this.productHome = StringUtils.isBlank(productHome)? null : productHome.trim();
    }

    public Boolean getPerfectProduct() {
        return perfectProduct;
    }

    public void setPerfectProduct(Boolean perfectProduct) {
        this.perfectProduct = perfectProduct;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = StringUtils.isBlank(productCompany)? null : productCompany.trim();
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Integer getRemarks() {
        return remarks;
    }

    public void setRemarks(Integer remarks) {
        this.remarks = remarks;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = StringUtils.isBlank(remarks1)? null : remarks1.trim();
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = StringUtils.isBlank(remarks2)? null : remarks2.trim();
    }

    public Long getTempAdminId() {
        return tempAdminId;
    }

    public void setTempAdminId(Long tempAdminId) {
        this.tempAdminId = tempAdminId;
    }
}