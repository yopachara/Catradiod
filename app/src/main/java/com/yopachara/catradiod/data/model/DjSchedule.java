
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DjSchedule {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("data")
    @Expose
    private Data data;

    public DjSchedule() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DjSchedule{" +
                "total=" + total +
                ", page=" + page +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
