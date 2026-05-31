package br.unifor.produtosapi.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ObservabilidadeAspect {

    private static final Logger log = LoggerFactory.getLogger(ObservabilidadeAspect.class);

    /**
     * Pointcut base da observabilidade.
     *
     * Intercepta todos os métodos das camadas de controller e service.
     */
    @Pointcut("within(br.unifor.produtosapi.controller..*) || within(br.unifor.produtosapi.service..*)")
    public void camadasApi() {
        // Pointcut nomeado: intercepta controllers e services da API.
    }

    /**
     * Registra os parâmetros de entrada antes da execução do método alvo.
     *
     * @param joinPoint contexto da chamada interceptada
     */
    @Before("camadasApi()")
    public void logarEntradas(JoinPoint joinPoint) {
        // Advice Before: executa antes do metodo alvo e registra os argumentos recebidos.
        String metodo = joinPoint.getSignature().toShortString();
        Object[] argumentos = joinPoint.getArgs();
        String parametros = argumentos == null ? "[]" : Arrays.deepToString(argumentos);

        // Log funcional: mostra o método chamado e os parâmetros recebidos.
        log.info("ENTRADA metodo={} parametros={}", metodo, parametros);
    }

    /**
     * Registra o valor de retorno no caminho de sucesso da execução.
     *
     * @param joinPoint contexto da chamada interceptada
     * @param retorno valor retornado pelo método alvo
     */
    @AfterReturning(pointcut = "camadasApi()", returning = "retorno")
    public void logarRetornos(JoinPoint joinPoint, Object retorno) {
        // Advice AfterReturning: executa so no caminho de sucesso e recebe o valor de retorno.
        String metodo = joinPoint.getSignature().toShortString();
        String saida = retorno == null ? "null" : String.valueOf(retorno);

        // Log funcional: registra o retorno no caminho de sucesso.
        log.info("SAIDA metodo={} retorno={}", metodo, saida);
    }

    /**
     * Envolve a execução para medir tempo total e registrar status final.
     *
     * @param joinPoint contexto da chamada interceptada
     * @return retorno original do método alvo
     * @throws Throwable repassa a exceção original da execução
     */
    @Around("camadasApi()")
    public Object registrarTempoEStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        // Advice Around: envolve a execucao para medir tempo e registrar sucesso/erro.
        String metodo = joinPoint.getSignature().toShortString();
        int quantidadeArgumentos = joinPoint.getArgs() == null ? 0 : joinPoint.getArgs().length;
        long inicio = System.nanoTime();

        // Log técnico: marca o início para rastreabilidade da execução.
        log.info("INICIO metodo={} args={}", metodo, quantidadeArgumentos);

        try {
            Object retorno = joinPoint.proceed();
            long tempoMs = (System.nanoTime() - inicio) / 1_000_000;
            // Log técnico: registra fim com tempo total e status de sucesso.
            log.info("FIM metodo={} tempoMs={} sucesso=true", metodo, tempoMs);
            return retorno;
        } catch (Throwable ex) {
            long tempoMs = (System.nanoTime() - inicio) / 1_000_000;
            // Log técnico: registra falha com tipo e mensagem da exceção.
            log.warn(
                "FIM metodo={} tempoMs={} sucesso=false erro={} mensagem={}",
                metodo,
                tempoMs,
                ex.getClass().getSimpleName(),
                ex.getMessage()
            );
            throw ex;
        }
    }
}
