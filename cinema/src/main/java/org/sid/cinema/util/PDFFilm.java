package org.sid.cinema.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.sid.cinema.entities.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class PDFFilm {

    private static Logger logger = LoggerFactory.getLogger(PDFFilm.class);

    public static ByteArrayInputStream lisFilmsPDF(List<Film> films){

        Document document = new Document();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();
            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Lites des films", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            for(Film film: films){
                File file = new File(System.getProperty("user.home")+"/cinema/images/"+film.getPhoto());
                Image img = Image.getInstance(String.format(file.toString(), "0120903"));

                img.scaleToFit(200, 200);


                PdfPTable table = new PdfPTable(3);
                table.setWidths(new float[] {2,2,2});

                table.setWidthPercentage(100);
                table.setSpacingBefore(5);

                PdfPCell cell1;
                cell1 = new PdfPCell(new Phrase("Titre:", FilmFonts.BOLD));
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);

                PdfPCell cell2;
                cell2 = new PdfPCell(new Phrase(film.getTitre(), FilmFonts.BOLDITALIC));
                cell2.setColspan(2);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(img);
                cell3.setRowspan(4);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell3);

                PdfPCell idcell = new PdfPCell(new Phrase("ID:",FilmFonts.BOLD));
                idcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                idcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idcell);

                PdfPCell idvalcell = new PdfPCell(new Phrase(film.getId().toString(),FilmFonts.ITALIC));
                idvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idvalcell);

                PdfPCell dureecell = new PdfPCell(new Phrase("Duree:",FilmFonts.BOLD));
                dureecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                dureecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(dureecell);

                PdfPCell dureevalcell = new PdfPCell(new Phrase(String.valueOf(film.getDuree()),FilmFonts.ITALIC));
                dureevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(dureevalcell);


                PdfPCell realisateurcell = new PdfPCell(new Phrase("Réalisateur:",FilmFonts.BOLD));
                realisateurcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                realisateurcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(realisateurcell);

                PdfPCell realisateuvalrcell = new PdfPCell(new Phrase(film.getRealisateurs(),FilmFonts.ITALIC));
                realisateuvalrcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(realisateuvalrcell);

                PdfPCell datesortiecell = new PdfPCell(new Phrase("DateSortie:",FilmFonts.BOLD));
                datesortiecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                datesortiecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(datesortiecell);

                PdfPCell datesortievalcell = new PdfPCell(new Phrase(String.valueOf(film.getDateSortie()),FilmFonts.ITALIC));
                datesortievalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(datesortievalcell);


                PdfPCell descriptioncell = new PdfPCell(new Phrase("Description:",FilmFonts.BOLD));
                descriptioncell.setRowspan(3);
                descriptioncell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                descriptioncell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(descriptioncell);

                PdfPCell descriptionvalcell = new PdfPCell(new Phrase(film.getDescription(),FilmFonts.ITALIC));
                descriptionvalcell.setRowspan(3);
                descriptionvalcell.setColspan(2);
                descriptionvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(descriptionvalcell);

                table.setTotalWidth(100);

                System.out.println(table.getTotalHeight()+"/"+writer.getVerticalPosition(true));

                if(table.getTotalHeight() > writer.getVerticalPosition(true)) {
                    document.newPage();
                }

                document.add(table);

                document.add(Chunk.NEWLINE);

            }

            document.close();

        } catch (DocumentException e) {
            logger.error(e.toString());
        } catch (MalformedURLException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }



    public static ByteArrayInputStream FilmPDF(Film film){

        Document document = new Document();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();
            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Lites des films", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
                File file = new File(System.getProperty("user.home")+"/cinema/images/"+film.getPhoto());
                Image img = Image.getInstance(String.format(file.toString(), "0120903"));

                img.scaleToFit(200, 200);


                PdfPTable table = new PdfPTable(3);
                table.setWidths(new float[] {2,2,2});

                table.setWidthPercentage(100);
                table.setSpacingBefore(5);

                PdfPCell cell1;
                cell1 = new PdfPCell(new Phrase("Titre:", FilmFonts.BOLD));
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);

                PdfPCell cell2;
                cell2 = new PdfPCell(new Phrase(film.getTitre(), FilmFonts.BOLDITALIC));
                cell2.setColspan(2);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(img);
                cell3.setRowspan(4);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell3);

                PdfPCell idcell = new PdfPCell(new Phrase("ID:",FilmFonts.BOLD));
                idcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                idcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idcell);

                PdfPCell idvalcell = new PdfPCell(new Phrase(film.getId().toString(),FilmFonts.ITALIC));
                idvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idvalcell);

                PdfPCell dureecell = new PdfPCell(new Phrase("Duree:",FilmFonts.BOLD));
                dureecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                dureecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(dureecell);

                PdfPCell dureevalcell = new PdfPCell(new Phrase(String.valueOf(film.getDuree()),FilmFonts.ITALIC));
                dureevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(dureevalcell);


                PdfPCell realisateurcell = new PdfPCell(new Phrase("Réalisateur:",FilmFonts.BOLD));
                realisateurcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                realisateurcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(realisateurcell);

                PdfPCell realisateuvalrcell = new PdfPCell(new Phrase(film.getRealisateurs(),FilmFonts.ITALIC));
                realisateuvalrcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(realisateuvalrcell);

                PdfPCell datesortiecell = new PdfPCell(new Phrase("DateSortie:",FilmFonts.BOLD));
                datesortiecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                datesortiecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(datesortiecell);

                PdfPCell datesortievalcell = new PdfPCell(new Phrase(String.valueOf(film.getDateSortie()),FilmFonts.ITALIC));
                datesortievalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(datesortievalcell);


                PdfPCell descriptioncell = new PdfPCell(new Phrase("Description:",FilmFonts.BOLD));
                descriptioncell.setRowspan(3);
                descriptioncell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                descriptioncell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(descriptioncell);

                PdfPCell descriptionvalcell = new PdfPCell(new Phrase(film.getDescription(),FilmFonts.ITALIC));
                descriptionvalcell.setRowspan(3);
                descriptionvalcell.setColspan(2);
                descriptionvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(descriptionvalcell);

                table.setTotalWidth(100);


                document.add(table);
                document.close();

        } catch (DocumentException e) {
            logger.error(e.toString());
        } catch (MalformedURLException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
