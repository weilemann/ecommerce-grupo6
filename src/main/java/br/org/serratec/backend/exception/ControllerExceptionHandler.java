package br.org.serratec.backend.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(CpfException.class)
	public ResponseEntity<Object> handleCpfException(CpfException ex){
		CpfException cpfException = new CpfException(ex.getMessage());
		return ResponseEntity.unprocessableEntity().body(cpfException);
	}
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Object> handleEmailException(EmailException ex){
		EmailException emailException = new EmailException(ex.getMessage());
		return ResponseEntity.unprocessableEntity().body(emailException);
	}
	@ExceptionHandler(UsuarioException.class)
	public ResponseEntity<Object> handleUsuarioException(UsuarioException ex){
		UsuarioException usuarioException = new UsuarioException(ex.getMessage());
		return ResponseEntity.unprocessableEntity().body(usuarioException);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> erros = new ArrayList<String>();

		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			erros.add(erro.getField() + ":" + erro.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),erros);
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}
}
