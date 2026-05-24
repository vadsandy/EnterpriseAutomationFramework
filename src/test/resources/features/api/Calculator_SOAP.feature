@Regression @API @SOAP
Feature: Legacy SOAP API Validation

  Scenario: Validate SOAP Addition Service
    Given the API base URI is "http://www.dneonline.com/calculator.asmx"
    And I set the Content-Type header to "text/xml; charset=utf-8"
    And I set the SOAP request body to:
      """
      <?xml version="1.0" encoding="utf-8"?>
      <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Body>
          <Add xmlns="http://tempuri.org/">
            <intA>5</intA>
            <intB>10</intB>
          </Add>
        </soap:Body>
      </soap:Envelope>
      """
    When I send a POST request to ""
    Then the API response status code should be 200
    And the response XML should contain "Envelope.Body.AddResponse.AddResult" as "15"