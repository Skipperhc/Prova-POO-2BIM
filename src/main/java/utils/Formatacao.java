package utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import exceptions.FormatacaoException;

public class Formatacao {

	public static void main(String[] args) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			System.out.println("CNPJ : " + mask.valueToString("09574303969"));
		} catch (ParseException ex) {
			System.out.println("deu ruim");
		}
	}

	public static String formatarDocumento(String cpf_RG) {
		cpf_RG = cpf_RG.replaceAll("[^0-9]+", "");
		String cpfRGFormatado = "";
		try {
			if (cpf_RG.length() == 11) {
				MaskFormatter mask = new MaskFormatter("###.###.###-##");
				mask.setValueContainsLiteralCharacters(false);
				cpfRGFormatado = mask.valueToString(cpf_RG);
			} else if (cpf_RG.length() == 14) {
				MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
				mask.setValueContainsLiteralCharacters(false);
				cpfRGFormatado = mask.valueToString(cpf_RG);
			} else if (cpf_RG.length() == 9) {
				MaskFormatter mask = new MaskFormatter("##.###.###-#");
				mask.setValueContainsLiteralCharacters(false);
				cpfRGFormatado = mask.valueToString(cpf_RG);
			} else {
				throw new FormatacaoException();
			}
			return cpfRGFormatado;
		} catch (Exception e) {
			throw new FormatacaoException("Insira um documento válido");
		}
	}

	public static String formatarTelefone(String telefone) {
		telefone = telefone.replaceAll("[^0-9]+", "");
		String telefoneFormatado = "";
		try {
			if (telefone.length() == 8) {
				MaskFormatter mask = new MaskFormatter("####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 9) {
				MaskFormatter mask = new MaskFormatter("# ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 10) {
				MaskFormatter mask = new MaskFormatter("(##) ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 11) {
				MaskFormatter mask = new MaskFormatter("(##) # ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else {
				throw new FormatacaoException();
			}
			
			return telefoneFormatado;
		} catch (Exception e) {
			throw new FormatacaoException("Insira um telefone");
		}
	}
}
