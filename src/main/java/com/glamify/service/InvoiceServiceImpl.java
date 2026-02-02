package com.glamify.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.glamify.entity.Appointment;
import com.glamify.entity.Invoice;
import com.glamify.entity.Payment;
import com.glamify.entity.PaymentStatus;
import com.glamify.repository.InvoiceRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice generateInvoice(Appointment appointment, Payment payment) {

        // Defensive check – business invariant
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException(
                "Invoice can only be generated for successful payments"
            );
        }

        Invoice invoice = new Invoice();
        invoice.setAppointment(appointment);
        invoice.setPayment(payment);

        invoice.setInvoiceNumber(generateInvoiceNumber());

        invoice.setBaseAmount(payment.getAmount());
        invoice.setTaxAmount(calculateTax(payment.getAmount()));
        invoice.setTotalAmount(payment.getAmount() + invoice.getTaxAmount());

        invoice.setGeneratedAt(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    private double calculateTax(double amount) {
        return amount * 0.18; // 18% GST
    }
    
    @Override
    public byte[] generateInvoicePdf(String invoiceNumber) {

        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = invoice.getPayment();
        Appointment appointment = payment != null ? payment.getAppointment() : null;

        // ========= SAFE EXTRACTION (NO NULL CHAINS) =========
        String customerName = "Customer";
        String location = "-";
        String appointmentDate = "-";
        String transactionId = payment != null && payment.getTransactionId() != null
                ? payment.getTransactionId()
                : "-";

        if (appointment != null) {
            if (appointment.getCustomer() != null &&
                appointment.getCustomer().getFullName() != null) {
                customerName = appointment.getCustomer().getFullName();
            }

            if (appointment.getLocation() != null) {
                location = appointment.getLocation();
            }

            if (appointment.getDateTime() != null) {
                appointmentDate = appointment.getDateTime()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
            }
        }

        double baseAmount = invoice.getBaseAmount();
		

        double taxAmount = invoice.getTaxAmount();

        double totalAmount = invoice.getTotalAmount();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, out);

            document.open();

            Image logo = null;
            try {
                logo = Image.getInstance(
                    Objects.requireNonNull(
                        getClass().getClassLoader().getResource("static/images/glamify-logo-black.png")
                    )
                );
                logo.scaleToFit(80, 80);
            } catch (Exception e) {
                // logo is optional – PDF should still generate
            }
            
            // ========= FONTS =========
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11);
            Font grayFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.GRAY);

            // ========= HEADER =========
//            PdfPTable header = new PdfPTable(2);
//            header.setWidthPercentage(100);
//            header.setWidths(new int[]{70, 30});
//
//            PdfPCell left = new PdfPCell(new Phrase("GLAMIFY\nBeauty Services", titleFont));
//            left.setBorder(Rectangle.NO_BORDER);
//
//            PdfPCell right = new PdfPCell(new Phrase(
//                    "INVOICE\n\nInvoice No: " + invoice.getInvoiceNumber(),
//                    boldFont
//            ));
//            right.setBorder(Rectangle.NO_BORDER);
//            right.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//            header.addCell(left);
//            header.addCell(right);
//            document.add(header);
//
//            document.add(Chunk.NEWLINE);

            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new int[]{15, 70});

            // LOGO CELL
            PdfPCell logoCell;
            if (logo != null) {
                logoCell = new PdfPCell(logo, false);
            } else {
                logoCell = new PdfPCell(new Phrase(""));
            }
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setRowspan(2);
            header.addCell(logoCell);

            /*
            // COMPANY NAME
            PdfPCell companyCell = new PdfPCell(
                    new Phrase("Personal Care Services", titleFont)
            );
            companyCell.setBorder(Rectangle.NO_BORDER);
            header.addCell(companyCell);
			*/
            
            // INVOICE INFO
            PdfPCell invoiceCell = new PdfPCell(
                    new Phrase(
                        "INVOICE\n\nInvoice No: " + invoice.getInvoiceNumber(),
                        boldFont
                    )
            );
            
            invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            invoiceCell.setBorder(Rectangle.NO_BORDER);
            header.addCell(invoiceCell);

            document.add(header);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // ========= CUSTOMER / APPOINTMENT INFO =========
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(100);
            info.setWidths(new int[]{50, 50});

            info.addCell(noBorderCell("Billed To:", boldFont));
            info.addCell(noBorderCell("Appointment Details:", boldFont));

            info.addCell(noBorderCell(
                    customerName + "\n" + location,
                    normalFont
            ));

            info.addCell(noBorderCell(
                    "Date: " + appointmentDate + "\nStatus: PAID",
                    normalFont
            ));

            document.add(info);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // ========= AMOUNT TABLE =========
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{70, 30});

            table.addCell(tableHeader("Description"));
            table.addCell(tableHeader("Amount"));

            table.addCell(tableCell("Service Charges"));
            table.addCell(tableCell("₹ " + baseAmount));

            table.addCell(tableCell("GST"));
            table.addCell(tableCell("₹ " + taxAmount));

            table.addCell(tableTotal("TOTAL"));
            table.addCell(tableTotal("₹ " + totalAmount));

            document.add(table);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // ========= FOOTER =========
            document.add(new Paragraph(
                    "Transaction ID: " + transactionId,
                    grayFont
            ));

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(
                    "Thank you for choosing Glamify ❤️",
                    grayFont
            ));

            // 🔑 MUST CLOSE DOCUMENT
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }


    
    private PdfPCell noBorderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell tableHeader(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell tableCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell tableTotal(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setPadding(8);
        return cell;
    }



}
