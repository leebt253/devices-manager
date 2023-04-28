package net.posco.user.export;

import java.io.IOException;
import java.util.List;
import java.awt.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import net.posco.user.User;
import net.posco.util.AbstractExporter;

public class UserPdfExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "Users_", "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph copywright = new Paragraph("Posco ICT");
        copywright.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(copywright);
        Paragraph title = new Paragraph("LIST OF USERS", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[] { 1.0f, 4.0f, 3.0f, 3.0f, 2.0f, 2.0f });

        writeTableHeader(table);
        writeTableData(table, listUsers);

        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(14);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Role", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<User> listUsers) {
        for (User user : listUsers) {
            Integer id = user.getId();
            String email = user.getEmail();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String role = user.getRole();
            Boolean enabled = user.isEnabled();
            String status;
            if (enabled)
                status = "Enable";
            else
                status = "Disable";
            table.addCell(String.valueOf(id));
            table.addCell(email);
            table.addCell(firstName);
            table.addCell(lastName);
            table.addCell(role);
            table.addCell(status);
        }
    }
}
