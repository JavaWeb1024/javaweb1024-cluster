/*
* @ClassName:Products.java
* @Description: TODO(������һ�仰��������������) 
* @author: 
*-----------Zihan--www.javaweb1024.com ��Ȩ����------------
* @date 2016-10-13
*/
package com.dinghao.entity.template.products;

import java.util.Date;

public class Products {
    /**
     * .
     */
    private Long id;

    /**
     * .����ʱ��
     */
    private Date createDate;

    /**
     * .�޸�ʱ��
     */
    private Date modifyDate;

    /**
     * .�޸���ԱID
     */
    private Long modifyBy;

    /**
     * .��Ʒ���,1��ˮ,2 ����
     */
    private Integer productCategory;

    /**
     * .��Ʒ����
     */
    private String productName;

    /**
     * .��Ʒ���
     */
    private String productNumber;

    /**
     * .��Ʒ����
     */
    private String productCapacity;

    /**
     * .��Ʒ����
     */
    private String productMaterial;

    /**
     * .��������
     */
    private String productHome;

    /**
     * .�Ƿ��Ƽ���Ʒ
     */
    private Boolean perfectProduct;

    /**
     * .������ҵ
     */
    private String productCompany;

    /**
     * .��������
     */
    private Date productDate;

    /**
     * .������ԱID
     */
    private Long createBy;

    /**
     * .����(������)
     */
    private Integer remarks;

    /**
     * .��Ʒ���ӵ�ַ
     */
    private String remarks1;

    /**
     * .����2
     */
    private String remarks2;

    /**
     * .�̻�����
     */
    private Long tempAdminId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

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