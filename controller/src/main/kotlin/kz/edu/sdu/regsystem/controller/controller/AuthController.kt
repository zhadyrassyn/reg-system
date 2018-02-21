package kz.edu.sdu.regsystem.controller.controller

import kz.edu.sdu.regsystem.controller.model.AuthRequest
import kz.edu.sdu.regsystem.controller.register.AuthRegister
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(val authRegister: AuthRegister) {

    @PostMapping("/signin", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signIn(@RequestBody singInRequest: AuthRequest) = authRegister.signIn(singInRequest)

    @PostMapping("/signup", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@RequestBody signUpRequest: AuthRequest) = authRegister.signUp(signUpRequest)

    @GetMapping("/token/{token}")
    @ResponseStatus(HttpStatus.OK)
    fun verifyUser(@PathVariable token: String) = authRegister.verifyUser(token)
}