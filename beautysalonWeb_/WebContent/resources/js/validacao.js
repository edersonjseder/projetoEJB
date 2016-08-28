function validarEmail() {
	var email = document.getElementById("email").value;
	if(email != "")
	{
		var filtro = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if(filtro.test(email))
		{
			return true;
		} else {
			alert("Este endereço de email não é válido!");
			document.frmFunc.email.focus();
			return false;
		}
	} else {
		alert('Digite um email!'); 
		document.frmFunc.email.focus();
		return false;
	}
}

function formatar(src, mask){
	var i = src.value.length;
	var saida = mask.substring(0,1);
	var texto = mask.substring(i);
	if (texto.substring(0,1) != saida){
		src.value += texto.substring(0,1);
	}
}

function carregaData(src){
	var today = new Date();
	var todayd=today.getDate();
	var todaym=today.getMonth();
	var todayy=today.getFullYear();
	
	src.value = todayd + "/" + todaym + "/" + todayy;
}

function moeda(z){  
	v = z.value;
	v=v.replace(/\D/g,"");  //permite digitar apenas números
	v=v.replace(/[0-9]{12}/,"inválido");   //limita pra máximo 999.999.999,99
	v=v.replace(/(\d{1})(\d{8})$/,"$1.$2");  //coloca ponto antes dos últimos 8 digitos
	v=v.replace(/(\d{1})(\d{5})$/,"$1.$2");  //coloca ponto antes dos últimos 5 digitos
	v=v.replace(/(\d{1})(\d{1,2})$/,"$1,$2");        //coloca virgula antes dos últimos 2 digitos
	z.value = v;
}

/*consistencia se o valor do CPF e um valor valido
seguindo os criterios da Receita Federal do territorio nacional*/
function consistenciaCPF(campo) {
 
      cpf = campo.replace(/\./g, '').replace(/\-/g, '');
      erro = new String;
      if (cpf.length < 11) erro += "São necessários 11 dígitos para verificação do CPF! \n\n";
      var nonNumbers = /\D/;
      if (cpf == "00000000000" || cpf == "11111111111"
           || cpf == "22222222222" || cpf == "33333333333"
           || cpf == "44444444444" || cpf == "55555555555"
           || cpf == "66666666666" || cpf == "77777777777"
           || cpf == "88888888888" || cpf == "99999999999"){
              erro += "Número de CPF inválido!";
     }
     var a = [];
     var b = new Number;
     var c = 11;
     for (i=0; i<11; i++){
             a[i] = cpf.charAt(i);
             if (i < 9) b += (a[i] * --c);
     }
     if ((x = b % 11) < 2) { a[9] = 0; } else { a[9] = 11-x; }
     b = 0;
     c = 11;
     for (y=0; y<10; y++) b += (a[y] * c--);
     if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
     if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
             erro +="Número de CPF inválido!";
     }
     if (erro.length > 0){
        alert(erro);
        campo.focus();
        return true;
     }
     return false;
}

function numero(e){
	if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		return false;
	}
}

function calcularValorEntrada(valor){
	var variavel = parseFloat(valor.value.replace(",", "."));
	var aux = eval(document.getElementById('qtdeEntrada').value);
	
	var resultado = (variavel * aux);
	var valorCasaDecimal = resultado.toFixed(2);
	
	document.getElementById('valorTotal').value = valorCasaDecimal.replace(".", ",");
}

function calcularValorSaida(valor){
	var variavel = parseFloat(valor.value.replace(",", "."));
	var aux = eval(document.getElementById('qtdeSaida').value);
	
	var resultado = (variavel * aux);
	var valorCasaDecimal = resultado.toFixed(2);
	
	document.getElementById('valorTotal').value = valorCasaDecimal.replace(".", ",");
}

function calcularDivisao(valor){
	var aux = eval(valor.value);
	var valorPagamento = parseFloat(document.getElementById('valorPagamento').value.replace(".", "").replace(",", "."));
	if(aux != ''){
		var parcela = (valorPagamento / aux);
		var parcelaCasaDecimal = parcela.toFixed(2);
		
		document.getElementById('valorParcela').value = formatarMoeda(parcelaCasaDecimal);
	}
	
}

function calcularValorParcela(valor){
	var variavel = parseFloat(valor.value.replace(".", "").replace(",", "."));
	var aux = eval(document.getElementById('nroParcela').value);

	alert(variavel);
	if(aux != ''){
		var resultado = (variavel * aux);
		var valorCasaDecimal = resultado.toFixed(2);
		document.getElementById('totalPagamento').value = formatarMoeda(valorCasaDecimal);
	}
}

function show(z){
	alert("Show " + z);
}

function arredondar(valor,casas){
	var novo = Math.round(valor*Math.pow(10,casas))/Math.pow(10,casas);
	return novo;
}

	/* Arredondar para Cima */
function arredondarCima(num, casas){
	    return Math.abs(Math.ceil(num * Math.pow(10, casas)) / Math.pow(10, casas++));
}
function formatarMoeda(z){  
	v = z;
	v=v.replace(/\D/g,"");  //permite digitar apenas números
	v=v.replace(/[0-9]{12}/,"inválido");   //limita pra máximo 999.999.999,99
	v=v.replace(/(\d{1})(\d{8})$/,"$1.$2");  //coloca ponto antes dos últimos 8 digitos
	v=v.replace(/(\d{1})(\d{5})$/,"$1.$2");  //coloca ponto antes dos últimos 5 digitos
	v=v.replace(/(\d{1})(\d{1,2})$/,"$1,$2");        //coloca virgula antes dos últimos 2 digitos
	z = v;
	return z;
}

//--->Função para a formatação dos campos...<---
function Mascara(campo, teclaPress) {
	if (window.event) {
		var tecla = teclaPress.keyCode;
	} else {
		tecla = teclaPress.which;
	}
	
	var s = new String(campo.value);
	
	if(s.length == 11){
		if(!consistenciaCPF(s)){
			// Remove todos os caracteres à seguir: ( ) / - . e espaço, para tratar a string denovo.
			s = s.replace(/(\.|\(|\)|\/|\-| )+/g,'');
			
			tam = s.length + 1;
			
			if ( tecla != 9 && tecla != 8 ) {
				if (tam > 3 && tam < 7)
					campo.value = s.substr(0,3) + '.' + s.substr(3, tam);
				if (tam >= 7 && tam < 10)
					campo.value = s.substr(0,3) + '.' + s.substr(3,3) + '.' + s.substr(6,tam-6);
				if (tam >= 10 && tam < 12)
					campo.value = s.substr(0,3) + '.' + s.substr(3,3) + '.' + s.substr(6,3) + '-' + s.substr(9,tam-9);
			}
		}else{
			return false;
		}
	} else if(s.length == 14){
		if(valida_cnpj(s)){
			if (tam > 2 && tam < 6)
				campo.value = s.substr(0,2) + '.' + s.substr(2, tam);
			if (tam >= 6 && tam < 9)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,tam-5);
			if (tam >= 9 && tam < 13)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,3) + '/' + s.substr(8,tam-8);
			if (tam >= 13 && tam < 15)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,3) + '/' + s.substr(8,4)+ '-' + s.substr(12,tam-12);
		}
	}
	
}

function valida_cnpj(cnpj) {
	var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
	digitos_iguais = 1;
	if (cnpj.length < 14 && cnpj.length < 15)
		return false;
	for (i = 0; i < cnpj.length - 1; i++)
		if (cnpj.charAt(i) != cnpj.charAt(i + 1))
		{
			digitos_iguais = 0;
			break;
		}
	if (!digitos_iguais)
	{
		tamanho = cnpj.length - 2;
		numeros = cnpj.substring(0,tamanho);
		digitos = cnpj.substring(tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--)
		{
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2)
				pos = 9;
		}
		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		if (resultado != digitos.charAt(0))
			return false;
		tamanho = tamanho + 1;
		numeros = cnpj.substring(0,tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--)
		{
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2)
				pos = 9;
		}
		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		if (resultado != digitos.charAt(1))
			return false;
		return true;
	}
	else
		return false;
}

//--->Função para verificar se o valor digitado é número...<---
function digitos(event){
	if (window.event) {
		// IE
		key = event.keyCode;
	} else if ( event.which ) {
		// netscape
		key = event.which;
	}
	if ( key != 8 || key != 13 || key < 48 || key > 57 )
		return ( ( ( key > 47 ) && ( key < 58 ) ) || ( key == 8 ) || ( key == 13 ) );
	return true;
}