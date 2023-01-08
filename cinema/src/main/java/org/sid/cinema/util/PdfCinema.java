package org.sid.cinema.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.sid.cinema.entities.Cinema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfCinema {

    private static Logger logger = LoggerFactory.getLogger(PdfCinema.class);

    public static ByteArrayInputStream cinemaPDF(List<Cinema> cinemas){

        Document document = new Document();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Liste des cinémas", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);

            // Add PDF Table Header ->
            Stream.of("ID", "Nom", "Longidude", "Latitude", "Altitude", "Nombre Salle")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Cinema cinema: cinemas){

                PdfPCell idCell = new PdfPCell(new Phrase(cinema.getId().toString()));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell nameNameCell = new PdfPCell(new Phrase(cinema.getName()));
                nameNameCell .setPaddingLeft(4);
                nameNameCell .setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameNameCell .setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameNameCell);

                PdfPCell LongitidudeCell = new PdfPCell(new Phrase(String.valueOf(cinema.getLongitude())));
                LongitidudeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                LongitidudeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                LongitidudeCell.setPaddingRight(4);
                table.addCell(LongitidudeCell);

                PdfPCell latitudCell = new PdfPCell(new Phrase(String.valueOf(cinema.getLatitude())));
                latitudCell.setPaddingLeft(4);
                latitudCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                latitudCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(latitudCell);

                PdfPCell AltitudeCell = new PdfPCell(new Phrase(String.valueOf(cinema.getAltitude())));
                AltitudeCell.setPaddingLeft(4);
                AltitudeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                AltitudeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(AltitudeCell);

                PdfPCell nomdreSalleCell = new PdfPCell(new Phrase(String.valueOf(cinema.getNombreSalles())));
                nomdreSalleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nomdreSalleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                nomdreSalleCell.setPaddingRight(4);
                table.addCell(nomdreSalleCell);

            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
