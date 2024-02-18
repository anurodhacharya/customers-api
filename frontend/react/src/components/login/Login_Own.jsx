'use client'
import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';

import {
  Button,
  Checkbox,
  Flex,
  Text,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Stack,
  Image,
  Alert,
  AlertIcon
} from '@chakra-ui/react'
import { Link } from 'react-router-dom';
import { saveCustomer } from '../../services/client';

const MyTextInput = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <>
      {console.log(field)}
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Input className="text-input" {...field} {...props} />
      {meta.touched && meta.error ? (
        <Alert className="error" status={"error"} mt={2}><AlertIcon></AlertIcon>{meta.error}</Alert>
      ) : null}
    </>
  );
};

const Login = SplitScreen => {
  return (
    <>
    {console.log("Inside Formik")}
    <Formik
      initialValues={{
        email: '',
        password: '',
      }}
      validationSchema={Yup.object({
        email: Yup.string()
          .email('Invalid email address')
          .required('Required'),
        password: Yup.string()
          .min(5, 'Must be at least 8 characters')
          .max(100, 'Must be less 100 characters')
          .required('Required'),
      })}
      onSubmit={(customer, { setSubmitting }) => {
        setSubmitting(true);
        // alert(customer);
        console.log(customer);
        saveCustomer(customer).then(res => {
          alert(JSON.stringify(customer, null, 2));
          alert("customer saved");
          
          console.log(res);
          successNotification("Customer saved", `${customer.name} was successfully saved`);
          fetchCustomers();
          // console.log(JSON.stringify(customer, null, 2))
        }).catch(err => {
            console.log("Inside catch");
            console.log(err);
            errorNotification(err.code, err.response.data.message);
        }).finally(() => {
            console.log("Inside finally");
            setSubmitting(false);
        })

        // console.log("HI");
      }}
    >

    {
      ({isValid}) => (
        
        <Form>
          { console.log("HIII") }
          <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
          {/* <Flex p={8} flex={1} align={'center'} justify={'center'} justifyContent={"center"} flexDirection={'column'}> */}
          <Flex p={8} flex={1} align={'center'} justify={'center'}>
            <Stack spacing={4} w={'full'} maxW={'md'}>
              <Heading fontSize={'2xl'}>Sign in to your account</Heading>
              <FormControl id="email">
                <FormLabel>Email address</FormLabel>
                <MyTextInput name='email' type="email" />
              </FormControl>
              <FormControl id="password">
                <FormLabel>Password</FormLabel>
                <MyTextInput name='password' type="password" />
              </FormControl>
              <Stack spacing={6}>
                <Stack
                  direction={{ base: 'column', sm: 'row' }}
                  align={'start'}
                  justify={'space-between'}>
                  <Checkbox>Remember me</Checkbox>
                  <Text color={'blue.500'}>Forgot password?</Text>
                </Stack>
                <Button isDisabled={!isValid} type='submit' colorScheme={'blue'} variant={'solid'}>
                  Sign in
                </Button>
              </Stack>
            </Stack>
          </Flex>
          <Flex flex={1} padding={10} flexDirection={"column"} alignItems={"center"} justifyItems={"center"} bgGradient={{sm: 'linear(to-r, blue.600, purple.600)'}}>
            <Text fontSize={"6xl"} color={"white"} fontWeight={"bold"} mb={5}>
                <Link href={"https://google.com"}>
                    Enroll Now
                </Link>
            </Text>
            <Image
              alt={'Login Image'}
              objectFit={'scale-down'}
              src={
                'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'
              }
            />
          </Flex>
        </Stack>
        </Form>
      )
    }
    
    </Formik>
    </>
  )
}

export default Login;