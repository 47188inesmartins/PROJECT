import {MDBBtn, MDBCheckbox, MDBCol, MDBContainer, MDBInput, MDBRow} from "mdb-react-ui-kit";
import React from "react";

import { useState } from 'react';

export function Login() {

    const [inputs, setInputs] = useState({
        email: "",
        password: "",
    })

    function handleEmailChange(ev: React.FormEvent<HTMLInputElement>) {
        const name = ev.currentTarget.name
        setInputs({ ...inputs, [name]: ev.currentTarget.value })
     //   setError(undefined)
    }

    function handlePasswordChange(ev: React.FormEvent<HTMLInputElement>) {
        const name = ev.currentTarget.name
        setInputs({ ...inputs, [name]: ev.currentTarget.value })
        //   setError(undefined)
    }

    function handleSubmit(ev: React.FormEvent<HTMLFormElement>){
        ev.preventDefault()
     //   setIsSubmitting(true)
    }


    return (
        <MDBContainer fluid className="p-3 my-5 h-custom">
            <MDBRow>
                <MDBCol col='10' md='6'>
                    <img
                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                        className="img-fluid"
                        alt="Sample image"
                    />
                </MDBCol>

                <MDBCol col='4' md='6'>
                    <br /><br /><br />
                    <div className="divider d-flex align-items-center my-4">
                        <p className="text-center fw-bold mx-3 mb-0">Log into your account</p>
                    </div>

                    <form onSubmit={handleSubmit}>
                        <MDBInput
                            wrapperClass='mb-4'
                            label='Email address'
                            id='formControlLg'
                            type='email'
                            size="lg"
                            value={inputs.email}
                            onChange={handleEmailChange}
                        />
                        <MDBInput
                            wrapperClass='mb-4'
                            label='Password'
                            id='formControlLg'
                            type='password'
                            size="lg"
                            value={inputs.password}
                            onChange={handlePasswordChange}
                        />

                        <div className="d-flex justify-content-between mb-4">
                            <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
                            <a href="!#">Forgot password?</a>
                        </div>

                        <div className='text-center text-md-start mt-4 pt-2'>
                            <MDBBtn className="mb-0 px-5" size='lg' type="submit">Login</MDBBtn>
                            <p className="small fw-bold mt-2 pt-1 mb-2">
                                Don't have an account? <a href="src/pages/Login#!" className="link-danger">Register</a>
                            </p>
                        </div>
                    </form>

                </MDBCol>

            </MDBRow>

        </MDBContainer>
    );
}