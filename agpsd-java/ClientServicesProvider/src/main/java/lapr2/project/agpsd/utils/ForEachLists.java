package lapr2.project.agpsd.utils;

import java.util.List;
import lapr2.project.agpsd.model.AcademicQualification;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Document;
import lapr2.project.agpsd.model.ProfessionalQualification;

/**
 *
 *
 */
public class ForEachLists {

    public static String categoryForEach(List<Category> list) {
        String str = "";
        for (Category cat : list) {
            str += cat.toString() + "\n";
        }
        return str;
    }

    public static String acadQualifForEach(List<AcademicQualification> list) {
        String str = "";
        for (AcademicQualification aq : list) {
            str += aq.toStringOneLine() + "\n";
        }
        return str;
    }

    public static String profQualifForEach(List<ProfessionalQualification> list) {
        String str = "";
        for (ProfessionalQualification pq : list) {
            str += pq.toString() + "\n";
        }
        return str;
    }

    public static String docsForEach(List<Document> list) {
        String str = "";
        for (Document doc : list) {
            str += doc.toString() + "\n";
        }
        return str;
    }

}
