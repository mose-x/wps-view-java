package com.web.wps.logic.entity;

import com.web.wps.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "w_file_watermark_t")
@EqualsAndHashCode(callSuper = true)
public class WatermarkEntity extends BaseEntity {

	/**

	 * type: 1,                 //水印类型， 0为无水印； 1为文字水印
            value: "禁止传阅",                    //文字水印的文字，当type为1时此字段必选
            fillstyle: "rgba( 192, 192, 192, 0.6 )",     //水印的透明度，非必选，有默认值
            font: "bold 20px Serif",        //水印的字体，非必选，有默认值
            rotate: -0.7853982,            //水印的旋转度，非必选，有默认值
            horizontal: 50,             //水印水平间距，非必选，有默认值
            vertical: 100             //水印垂直间距，非必选，有默认值

	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 40)
	private Long id;
	private String fileId;
	private Integer type = 0;
	private String value = "";
	private String fillstyle = "rgba( 192, 192, 192, 0.6 )";
	private String font = "bold 20px Serif";
	private BigDecimal rotate = new BigDecimal("0");
	private Integer horizontal = 50;
	private Integer vertical = 50;

	public WatermarkEntity(){super();}

	public WatermarkEntity(String fileId) {
		this.fileId = fileId;
		this.type = 0;
		this.value = "";
		this.fillstyle = "rgba( 192, 192, 192, 0.6 )";
		this.font = "bold 20px Serif";
		this.rotate = new BigDecimal("0");
		this.horizontal = 50;
		this.vertical = 50;
	}
}
