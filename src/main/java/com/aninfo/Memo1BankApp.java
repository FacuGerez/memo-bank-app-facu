package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		/*for (Transaction t : accountService.getTransactions(cbu)){
			transactionService.deleteById(t.getId(),cbu);
		}*/
		accountService.deleteById(cbu);

	}

	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		Account account = accountService.withdraw(cbu, sum);
		this.createTransaction(new Transaction(-sum),cbu);
		return account;
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		Account account = accountService.deposit(cbu, sum);
		this.createTransaction(new Transaction(sum),cbu);
		return account;
	}

	@PostMapping("/accounts/{cbu}/transaction")
	@ResponseStatus(HttpStatus.CREATED)
	public void createTransaction(@RequestBody Transaction transaction,@PathVariable Long cbu) {
		Transaction transaction1 = transactionService.createTransaction(transaction);
		accountService.addTransaction(transaction1,cbu);
	}

	@GetMapping("/accounts/{cbu}/transaction")
	public Collection<Transaction> getTransactions(@PathVariable Long cbu) {
		return accountService.getTransactions(cbu);
	}

	@GetMapping("/accounts/{cbu}/transaction/{id}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable Long id,@PathVariable Long cbu) {
		Optional<Transaction> transactionOptional = transactionService.serchTransactionById(accountService.getTransactions(cbu),id);
		return ResponseEntity.of(transactionOptional);
	}

	@DeleteMapping("/accounts/{cbu}/transaction/{id}")
	public void deleteTransaction(@PathVariable Long cbu, @PathVariable Long id) {
		accountService.deleteTransactionById(id,cbu);
		transactionService.deleteById(id);
	}
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
