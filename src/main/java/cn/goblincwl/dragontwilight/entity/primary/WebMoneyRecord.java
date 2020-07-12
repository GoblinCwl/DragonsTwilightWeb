package cn.goblincwl.dragontwilight.entity.primary;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-06-17 23:12
 */
@Entity
@Table(name = "web_money_record", schema = "mc_base", catalog = "")
public class WebMoneyRecord {
    private Integer id;
    private Integer money;
    private Integer moneyType;
    private String remarks;
    private Timestamp moneyDate;

    @Id
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MONEY", nullable = false, precision = 0)
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Basic
    @Column(name = "MONEY_TYPE", nullable = true)
    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    @Basic
    @Column(name = "REMARKS", nullable = true, length = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "MONEY_DATE", nullable = true)
    public Timestamp getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(Timestamp moneyDate) {
        this.moneyDate = moneyDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebMoneyRecord that = (WebMoneyRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(money, that.money) &&
                Objects.equals(moneyType, that.moneyType) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(moneyDate, that.moneyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, money, moneyType, remarks, moneyDate);
    }
}
