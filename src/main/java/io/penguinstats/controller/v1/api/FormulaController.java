package io.penguinstats.controller.v1.api;

import io.penguinstats.constant.Constant;
import io.penguinstats.constant.Constant.CustomHeader;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController("formulaController_v1")
@RequestMapping("/api/formula")
@Api(tags = {"@ Deprecated APIs"}, description = "Deprecated v1 APIs. Please use the v2 APIs instead of those ones.")
@Deprecated
public class FormulaController {

	@ApiOperation("Get formula")
	@GetMapping(produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> getFormula() {
		Resource resource = new ClassPathResource("json/formula.json");
		try {
			File sourceFile = resource.getFile();
			BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
			StringBuilder builder = new StringBuilder();
			String currentLine = reader.readLine();
			while (currentLine != null) {
				builder.append(currentLine).append("\n");
				currentLine = reader.readLine();
			}
			reader.close();

			HttpHeaders headers = new HttpHeaders();
			headers.add(CustomHeader.X_PENGUIN_UPGRAGE, Constant.API_V2);
			return new ResponseEntity<>(builder.toString(), headers, HttpStatus.OK);
		} catch (IOException e) {
			log.error("Error in getFormula: ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
