package com.edaochina.shopping.common.utils.pdf;


import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author jintian
 * @date 2019/5/29 17:19
 */
public class PdfCreateUtil {

    private static Logger logger = LoggerFactory.getLogger(PdfCreateUtil.class);

    public static boolean createPdf(String fileName, String fileContext, String fileHead) {
        Rectangle rect = new Rectangle(PageSize.A4);
        Document doc = new Document(rect, 50.0F, 50.0F, 50.0F, 50.0F);
        File file = new File(fileName);
        try {
            file.getParentFile().mkdirs();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            // 使用系统字体
            BaseFont bf = BaseFont.createFont("font/simsun.ttc,1",
                    "Identity-H", true);
            Font font = new Font(bf, 14f, 0);
            // 设置样式
            font.setStyle(37);
            // 设置字体
            font.setFamily("宋体");
            // 设置头部内容
            Paragraph p = new Paragraph(fileHead, font);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);

            Paragraph context = new Paragraph();
            Font contextFont = new Font(bf, 8, 0);
            // 设置样式
            //contextFont.setStyle(37);
            // 设置字体
            contextFont.setFamily("宋体");
            Chunk chunk = new Chunk(fileContext);
            chunk.setFont(contextFont);
            context.add(chunk);
            context.setAlignment(Element.ALIGN_LEFT);
            context.setLeading(12f);
            doc.add(context);
            doc.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        createPdf("E:/t.pdf",
                "　　甲方：______________有限公司\n" +
                        "\n" +
                        "　　乙方：_____________________\n" +
                        "\n" +
                        "　　身份证号码：______________\n" +
                        "\n" +
                        "　　根据《中华人民共和国劳动合同法》以及有关法律、法规和政策的规定，经双方平等协商，订立本用工合同。\n" +
                        "\n" +
                        "　　一、合同期限\n" +
                        "\n" +
                        "　　合同期限为一年。即自_______年_____月___日起至_______年_____月___日止。\n" +
                        "\n" +
                        "　　二、工作内容\n" +
                        "\n" +
                        "　　乙方同意按甲方工作需要，在_______岗位工作，履行职责，完成任务。\n" +
                        "\n" +
                        "　　乙方应遵守甲方依法制定的管理制度。\n" +
                        "\n" +
                        "　　三、劳动保护和工作条件\n" +
                        "\n" +
                        "　　甲方为乙方提供生产所需的劳动保护用品。乙方应严格遵守各项安全操作规程。\n" +
                        "\n" +
                        "　　四、工作报酬\n" +
                        "\n" +
                        "　　按双方商定的计件工资标准定时发放，不再享受其它福利待遇及费用。\n" +
                        "\n" +
                        "　　五、工作纪律\n" +
                        "\n" +
                        "　　1.乙方服从甲方的生产按排，完成生产任务，保证产品质量。\n" +
                        "\n" +
                        "　　2.特殊情况不能上班时需事先请假。\n" +
                        "\n" +
                        "　　3.爱护公共财产，保管好自己的工用具，发生丢失要给予赔偿。\n" +
                        "\n" +
                        "　　4.未经甲方允许，不得携带公司任何物品出岗。\n" +
                        "\n" +
                        "　　六、合同变更、终止、解除的条件\n" +
                        "\n" +
                        "　　如有违反甲方工作纪律的，甲方有权解除合同并提前通知乙方；乙方对甲方的工作安排、工资待遇或其它种种原因如有不满，同样有权解除合同，但需提前通知甲方。\n" +
                        "\n" +
                        "　　七、注意安全生产，乙方如违反操作规程所出现的事故，由乙方承担全部事故责任。\n" +
                        "\n" +
                        "　　八、本合同一式二份，甲乙双方各执一份。\n" +
                        "\n" +
                        "　　甲方（盖章）：______________乙方（签章）：______________\n" +
                        "\n" +
                        "　　_______年_____月___日 _______年_____月___日\n" +
                        "\n" +
                        "　　临时用工劳动合同简单范本（二）\n" +
                        "　　根据《_________》的规定，单位（以下简称甲方）因生产、工作需要招用_________同志（以下简称乙方）为临时工，经双方协商同意，签订本劳动合同。\n" +
                        "\n" +
                        "　　一、合同期限：\n" +
                        "\n" +
                        "　　本合同期限自_______年_____月___日起至_______年_____月___日止。合同期满即终止劳动合同。", "用户协议");
    }
}
