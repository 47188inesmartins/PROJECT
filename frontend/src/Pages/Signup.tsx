import * as React from 'react';
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardBody,
    MDBInput,
    MDBIcon
}
    from 'mdb-react-ui-kit';
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import {Navigate} from "react-router";

interface Categories {
    'BEAUTY',
    'LIFESTYLE',
    'FITNESS',
    'BUSINESS',
    'OTHERS'
}


interface UserCredentials{
    name: string,
    birthday:string
    email: string,
    password: string,
    interests: string
}


export function Signup() {

    const [name, setName] = useState<string>("")
    const [birthday, setBirthday] = useState<string>("")
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [submit, setSubmit] = useState<boolean>(false)
    const categories = ['BEAUTY', 'LIFESTYLE', 'FITNESS', 'BUSINESS', 'EDUCATION', 'OTHERS'];
    const [interests, setInterests] = useState("");

    const handleCategoryClick = (category) => {
        if (interests.includes(category)) {
            const updatedCategories = interests
                .split(",")
                .filter((cat) => cat !== category)
                .join(",");
            setInterests(updatedCategories);
        } else {
            const updatedCategories = interests
                ? interests + "," + category
                : category;
            setInterests(updatedCategories);
        }
    };



    const handleSubmit = () => {
        setSubmit(true)
    }


    function FetchSignup(){
        const userCredentials: UserCredentials = {
            name,
            birthday,
            email,
            password,
            interests
        }
        const resp = Fetch("/user", 'POST', userCredentials).response
        if(!resp) return(<p>...loading...</p>);

        if(resp.status) {
            setSubmit(false)
            window.location.href = `/`
            return(<Navigate to = '/'></Navigate>);
        }
        if(resp){
            window.location.href = `/`
            return(<Navigate to = '/'></Navigate>);
        }
    }

    return (
        <div>
            {!submit ?
                <MDBContainer fluid>
                    <MDBRow className='d-flex justify-content-center align-items-center h-100'>
                        <MDBCol col='12'>

                            <MDBCard className='bg-dark text-white my-5 mx-auto'
                                     style={{borderRadius: '1rem', maxWidth: '400px'}}>
                                <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>

                                    <h2 className="fw-bold mb-2 text-uppercase">Signup</h2>
                                    <p className="text-white-50 mb-5">Signup to our website!</p>

                                    <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                              labelClass='text-white'
                                              label='Name'
                                              id='formControlLg'
                                              type='text'
                                              size="lg"
                                              value={name}
                                              onChange={(e) => {
                                                console.log(e.target.value)
                                                  setName(e.target.value)

                                              }}
                                    />

                                    <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                              labelClass='text-white'
                                              label='Birthday'
                                              id='formControlLg'
                                              type='date'
                                              size="lg"
                                              value={birthday}
                                              onChange={(e) => setBirthday(e.target.value)}
                                    />

                                    <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                              labelClass='text-white'
                                              label='Email address'
                                              id='formControlLg'
                                              type='email'
                                              size="lg"
                                              value={email}
                                              onChange={(e) => {setEmail(e.target.value)
                                        console.log(e.target.value)}
                                    }

                                    />


                                    <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                              labelClass='text-white'
                                              label='Password'
                                              id='formControlLg'
                                              type='password'
                                              size="lg"
                                              value={password}
                                              onChange={(e) => setPassword(e.target.value)}
                                    />
                                    <a>What are your interests?</a>

                                    <div>
                                        {categories.map((category) => (
                                            <button
                                                key={category}
                                                onClick={() => handleCategoryClick(category)}
                                                style={{
                                                    backgroundColor: interests.includes(category) ? "pink" : "white",
                                                    padding: "8px 16px",
                                                    border: "1px solid pink",
                                                    borderRadius: "4px",
                                                    marginRight: "8px",
                                                    marginBottom: "8px",
                                                    cursor: "pointer",
                                                }}
                                            >
                                                {category}
                                            </button>
                                        ))}
                                    </div>
                                    <br/>
                                    <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                            onClick={handleSubmit}>Signup
                                    </button>

                                    <div className='d-flex flex-row mt-3 mb-5'>
                                        <MDBBtn tag='a' color='none' className='m-3' style={{color: 'white'}}>
                                            <MDBIcon fab icon='facebook-f' size="lg"/>
                                        </MDBBtn>

                                        <MDBBtn tag='a' color='none' className='m-3' style={{color: 'white'}}>
                                            <MDBIcon fab icon='twitter' size="lg"/>
                                        </MDBBtn>

                                        <MDBBtn tag='a' color='none' className='m-3' style={{color: 'white'}}>
                                            <MDBIcon fab icon='google' size="lg"/>
                                        </MDBBtn>
                                    </div>

                                    <div>
                                        <p className="mb-0">Already have an account? <a href="/login"
                                                                                      className="text-white-50 fw-bold">Login</a></p>

                                    </div>
                                </MDBCardBody>
                            </MDBCard>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
                :
                <FetchSignup/>
            }
        </div>
    );
}