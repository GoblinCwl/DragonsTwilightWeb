package com.goblincwl.dragontwilight.entity.slave;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-06-16 22:15
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vex_sign", schema = "mc_base")
public class VexSign {
    private Integer id;
    private String signUuid;
    private Integer signMonth;
    private Integer signDay1;
    private Integer signDay2;
    private Integer signDay3;
    private Integer signDay4;
    private Integer signDay5;
    private Integer signDay6;
    private Integer signDay7;
    private Integer signDay8;
    private Integer signDay9;
    private Integer signDay10;
    private Integer signDay11;
    private Integer signDay12;
    private Integer signDay13;
    private Integer signDay14;
    private Integer signDay15;
    private Integer signDay16;
    private Integer signDay17;
    private Integer signDay18;
    private Integer signDay19;
    private Integer signDay20;
    private Integer signDay21;
    private Integer signDay22;
    private Integer signDay23;
    private Integer signDay24;
    private Integer signDay25;
    private Integer signDay26;
    private Integer signDay27;
    private Integer signDay28;
    private Integer signDay29;
    private Integer signDay30;
    private Integer signDay31;
    private Integer signRew7;
    private Integer signRew14;
    private Integer signRew21;
    private Integer signRew28;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sign_uuid", nullable = false, length = 36)
    public String getSignUuid() {
        return signUuid;
    }

    public void setSignUuid(String signUuid) {
        this.signUuid = signUuid;
    }

    @Basic
    @Column(name = "sign_month", nullable = true)
    public Integer getSignMonth() {
        return signMonth;
    }

    public void setSignMonth(Integer signMonth) {
        this.signMonth = signMonth;
    }

    @Basic
    @Column(name = "sign_day1", nullable = true)
    public Integer getSignDay1() {
        return signDay1;
    }

    public void setSignDay1(Integer signDay1) {
        this.signDay1 = signDay1;
    }

    @Basic
    @Column(name = "sign_day2", nullable = true)
    public Integer getSignDay2() {
        return signDay2;
    }

    public void setSignDay2(Integer signDay2) {
        this.signDay2 = signDay2;
    }

    @Basic
    @Column(name = "sign_day3", nullable = true)
    public Integer getSignDay3() {
        return signDay3;
    }

    public void setSignDay3(Integer signDay3) {
        this.signDay3 = signDay3;
    }

    @Basic
    @Column(name = "sign_day4", nullable = true)
    public Integer getSignDay4() {
        return signDay4;
    }

    public void setSignDay4(Integer signDay4) {
        this.signDay4 = signDay4;
    }

    @Basic
    @Column(name = "sign_day5", nullable = true)
    public Integer getSignDay5() {
        return signDay5;
    }

    public void setSignDay5(Integer signDay5) {
        this.signDay5 = signDay5;
    }

    @Basic
    @Column(name = "sign_day6", nullable = true)
    public Integer getSignDay6() {
        return signDay6;
    }

    public void setSignDay6(Integer signDay6) {
        this.signDay6 = signDay6;
    }

    @Basic
    @Column(name = "sign_day7", nullable = true)
    public Integer getSignDay7() {
        return signDay7;
    }

    public void setSignDay7(Integer signDay7) {
        this.signDay7 = signDay7;
    }

    @Basic
    @Column(name = "sign_day8", nullable = true)
    public Integer getSignDay8() {
        return signDay8;
    }

    public void setSignDay8(Integer signDay8) {
        this.signDay8 = signDay8;
    }

    @Basic
    @Column(name = "sign_day9", nullable = true)
    public Integer getSignDay9() {
        return signDay9;
    }

    public void setSignDay9(Integer signDay9) {
        this.signDay9 = signDay9;
    }

    @Basic
    @Column(name = "sign_day10", nullable = true)
    public Integer getSignDay10() {
        return signDay10;
    }

    public void setSignDay10(Integer signDay10) {
        this.signDay10 = signDay10;
    }

    @Basic
    @Column(name = "sign_day11", nullable = true)
    public Integer getSignDay11() {
        return signDay11;
    }

    public void setSignDay11(Integer signDay11) {
        this.signDay11 = signDay11;
    }

    @Basic
    @Column(name = "sign_day12", nullable = true)
    public Integer getSignDay12() {
        return signDay12;
    }

    public void setSignDay12(Integer signDay12) {
        this.signDay12 = signDay12;
    }

    @Basic
    @Column(name = "sign_day13", nullable = true)
    public Integer getSignDay13() {
        return signDay13;
    }

    public void setSignDay13(Integer signDay13) {
        this.signDay13 = signDay13;
    }

    @Basic
    @Column(name = "sign_day14", nullable = true)
    public Integer getSignDay14() {
        return signDay14;
    }

    public void setSignDay14(Integer signDay14) {
        this.signDay14 = signDay14;
    }

    @Basic
    @Column(name = "sign_day15", nullable = true)
    public Integer getSignDay15() {
        return signDay15;
    }

    public void setSignDay15(Integer signDay15) {
        this.signDay15 = signDay15;
    }

    @Basic
    @Column(name = "sign_day16", nullable = true)
    public Integer getSignDay16() {
        return signDay16;
    }

    public void setSignDay16(Integer signDay16) {
        this.signDay16 = signDay16;
    }

    @Basic
    @Column(name = "sign_day17", nullable = true)
    public Integer getSignDay17() {
        return signDay17;
    }

    public void setSignDay17(Integer signDay17) {
        this.signDay17 = signDay17;
    }

    @Basic
    @Column(name = "sign_day18", nullable = true)
    public Integer getSignDay18() {
        return signDay18;
    }

    public void setSignDay18(Integer signDay18) {
        this.signDay18 = signDay18;
    }

    @Basic
    @Column(name = "sign_day19", nullable = true)
    public Integer getSignDay19() {
        return signDay19;
    }

    public void setSignDay19(Integer signDay19) {
        this.signDay19 = signDay19;
    }

    @Basic
    @Column(name = "sign_day20", nullable = true)
    public Integer getSignDay20() {
        return signDay20;
    }

    public void setSignDay20(Integer signDay20) {
        this.signDay20 = signDay20;
    }

    @Basic
    @Column(name = "sign_day21", nullable = true)
    public Integer getSignDay21() {
        return signDay21;
    }

    public void setSignDay21(Integer signDay21) {
        this.signDay21 = signDay21;
    }

    @Basic
    @Column(name = "sign_day22", nullable = true)
    public Integer getSignDay22() {
        return signDay22;
    }

    public void setSignDay22(Integer signDay22) {
        this.signDay22 = signDay22;
    }

    @Basic
    @Column(name = "sign_day23", nullable = true)
    public Integer getSignDay23() {
        return signDay23;
    }

    public void setSignDay23(Integer signDay23) {
        this.signDay23 = signDay23;
    }

    @Basic
    @Column(name = "sign_day24", nullable = true)
    public Integer getSignDay24() {
        return signDay24;
    }

    public void setSignDay24(Integer signDay24) {
        this.signDay24 = signDay24;
    }

    @Basic
    @Column(name = "sign_day25", nullable = true)
    public Integer getSignDay25() {
        return signDay25;
    }

    public void setSignDay25(Integer signDay25) {
        this.signDay25 = signDay25;
    }

    @Basic
    @Column(name = "sign_day26", nullable = true)
    public Integer getSignDay26() {
        return signDay26;
    }

    public void setSignDay26(Integer signDay26) {
        this.signDay26 = signDay26;
    }

    @Basic
    @Column(name = "sign_day27", nullable = true)
    public Integer getSignDay27() {
        return signDay27;
    }

    public void setSignDay27(Integer signDay27) {
        this.signDay27 = signDay27;
    }

    @Basic
    @Column(name = "sign_day28", nullable = true)
    public Integer getSignDay28() {
        return signDay28;
    }

    public void setSignDay28(Integer signDay28) {
        this.signDay28 = signDay28;
    }

    @Basic
    @Column(name = "sign_day29", nullable = true)
    public Integer getSignDay29() {
        return signDay29;
    }

    public void setSignDay29(Integer signDay29) {
        this.signDay29 = signDay29;
    }

    @Basic
    @Column(name = "sign_day30", nullable = true)
    public Integer getSignDay30() {
        return signDay30;
    }

    public void setSignDay30(Integer signDay30) {
        this.signDay30 = signDay30;
    }

    @Basic
    @Column(name = "sign_day31", nullable = true)
    public Integer getSignDay31() {
        return signDay31;
    }

    public void setSignDay31(Integer signDay31) {
        this.signDay31 = signDay31;
    }

    @Basic
    @Column(name = "sign_rew7", nullable = true)
    public Integer getSignRew7() {
        return signRew7;
    }

    public void setSignRew7(Integer signRew7) {
        this.signRew7 = signRew7;
    }

    @Basic
    @Column(name = "sign_rew14", nullable = true)
    public Integer getSignRew14() {
        return signRew14;
    }

    public void setSignRew14(Integer signRew14) {
        this.signRew14 = signRew14;
    }

    @Basic
    @Column(name = "sign_rew21", nullable = true)
    public Integer getSignRew21() {
        return signRew21;
    }

    public void setSignRew21(Integer signRew21) {
        this.signRew21 = signRew21;
    }

    @Basic
    @Column(name = "sign_rew28", nullable = true)
    public Integer getSignRew28() {
        return signRew28;
    }

    public void setSignRew28(Integer signRew28) {
        this.signRew28 = signRew28;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VexSign vexSign = (VexSign) o;
        return Objects.equals(id, vexSign.id) &&
                Objects.equals(signUuid, vexSign.signUuid) &&
                Objects.equals(signMonth, vexSign.signMonth) &&
                Objects.equals(signDay1, vexSign.signDay1) &&
                Objects.equals(signDay2, vexSign.signDay2) &&
                Objects.equals(signDay3, vexSign.signDay3) &&
                Objects.equals(signDay4, vexSign.signDay4) &&
                Objects.equals(signDay5, vexSign.signDay5) &&
                Objects.equals(signDay6, vexSign.signDay6) &&
                Objects.equals(signDay7, vexSign.signDay7) &&
                Objects.equals(signDay8, vexSign.signDay8) &&
                Objects.equals(signDay9, vexSign.signDay9) &&
                Objects.equals(signDay10, vexSign.signDay10) &&
                Objects.equals(signDay11, vexSign.signDay11) &&
                Objects.equals(signDay12, vexSign.signDay12) &&
                Objects.equals(signDay13, vexSign.signDay13) &&
                Objects.equals(signDay14, vexSign.signDay14) &&
                Objects.equals(signDay15, vexSign.signDay15) &&
                Objects.equals(signDay16, vexSign.signDay16) &&
                Objects.equals(signDay17, vexSign.signDay17) &&
                Objects.equals(signDay18, vexSign.signDay18) &&
                Objects.equals(signDay19, vexSign.signDay19) &&
                Objects.equals(signDay20, vexSign.signDay20) &&
                Objects.equals(signDay21, vexSign.signDay21) &&
                Objects.equals(signDay22, vexSign.signDay22) &&
                Objects.equals(signDay23, vexSign.signDay23) &&
                Objects.equals(signDay24, vexSign.signDay24) &&
                Objects.equals(signDay25, vexSign.signDay25) &&
                Objects.equals(signDay26, vexSign.signDay26) &&
                Objects.equals(signDay27, vexSign.signDay27) &&
                Objects.equals(signDay28, vexSign.signDay28) &&
                Objects.equals(signDay29, vexSign.signDay29) &&
                Objects.equals(signDay30, vexSign.signDay30) &&
                Objects.equals(signDay31, vexSign.signDay31) &&
                Objects.equals(signRew7, vexSign.signRew7) &&
                Objects.equals(signRew14, vexSign.signRew14) &&
                Objects.equals(signRew21, vexSign.signRew21) &&
                Objects.equals(signRew28, vexSign.signRew28);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signUuid, signMonth, signDay1, signDay2, signDay3, signDay4, signDay5, signDay6, signDay7, signDay8, signDay9, signDay10, signDay11, signDay12, signDay13, signDay14, signDay15, signDay16, signDay17, signDay18, signDay19, signDay20, signDay21, signDay22, signDay23, signDay24, signDay25, signDay26, signDay27, signDay28, signDay29, signDay30, signDay31, signRew7, signRew14, signRew21, signRew28);
    }
}
