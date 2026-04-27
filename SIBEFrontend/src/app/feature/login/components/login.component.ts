import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StateService } from 'src/app/shared/service/state.service';
import { StateProps } from 'src/app/shared/model/state.enum';
import { LoginService } from '../service/login.service';
import { Login } from '../model/login.model';
import { Router } from '@angular/router';
import { UserSession } from '../model/user-session.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  formularioLogin!: FormGroup;
  mensajeError: string = '';

  constructor(private formBuilder: FormBuilder,
    private stateService: StateService,
    private loginService: LoginService,
    private router: Router
  ) { }


  ngOnInit(): void {
    this.formularioLogin = this.formBuilder.group({
      userInput: ['', [Validators.required, Validators.email]],
      passwordInput: ['', Validators.required]
    });

    this.formularioLogin.get('userInput')?.valueChanges.subscribe(() => {
      this.mensajeError = '';
    });

    this.formularioLogin.get('passwordInput')?.valueChanges.subscribe(() => {
      this.mensajeError = '';
    });
  }

  login(): void {
    if (this.formularioLogin.invalid) {
      return;
    }

    const login: Login = {
      correo: this.formularioLogin.get('userInput')?.value,
      contrasena: this.formularioLogin.get('passwordInput')?.value
    };

    this.loginService.validarLogin(login).subscribe((response)=>{
        const token = window.sessionStorage.getItem('Authorization') === null ? '' : window.sessionStorage.getItem('Authorization') || '';
        const tokenPayload= JSON.parse(atob(token.split('.')[1]));
        const userSession: UserSession = {
          correo: tokenPayload.email,
          identificador: tokenPayload.id,
          authorities: tokenPayload.authorities.split(','),
          logged: true,
          rol: tokenPayload.rol || '',
          direccionId: tokenPayload.direccionId || '',
          areaId: tokenPayload.areaId || '',
          subareaId: tokenPayload.subareaId || ''
        };
        this.stateService.updateState(StateProps.USER_SESSION, userSession);
        this.router.navigate(['/home']);
      },
      (error)=> {
        console.error('Error durante el login:', error);
        this.mensajeError = 'Correo o contraseña incorrectos.';
      }
    );
  }
}
