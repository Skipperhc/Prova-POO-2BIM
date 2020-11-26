package utils.enums;

public enum EnumAlertTypes {
	infomacao(1), confirmacao(2), erro(3), atencao(4);

	public int valorAlert;

	EnumAlertTypes(int valor) {
		valorAlert = valor;
	}
}
