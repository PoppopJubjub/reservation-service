package com.popjub.reservationservice.application.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QrCodeService {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;

	public String generatedQrCodeImage(String qrData) {
		try {
			Map<EncodeHintType, Object> option = new HashMap<>();
			option.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			option.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			option.put(EncodeHintType.MARGIN, 1);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(
				qrData,
				BarcodeFormat.QR_CODE,
				WIDTH,
				HEIGHT,
				option
			);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
			byte[] imageBytes = outputStream.toByteArray();

			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (WriterException | IOException e) {
			throw new RuntimeException("QR 이미지 생성에 실패했습니다.", e);
		}
	}
}
