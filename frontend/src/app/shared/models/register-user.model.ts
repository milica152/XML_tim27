export class RegisterUser {
  username: string = "";
  password: string = "";
  name: string = "";
  surname: string = "";
  email: string = "";

  constructor(username: string, password: string, name:string, surname: string, email:string) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.username = username;
    this.surname = surname;
  }
}
