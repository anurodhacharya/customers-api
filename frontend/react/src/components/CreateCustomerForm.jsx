import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';

import {
    FormLabel,
    Input,
    Alert,
    Box,
    Select,
    AlertIcon,
    Button,
    Stack,
    useToast
} from "@chakra-ui/react";
import { saveCustomer } from '../services/client';
import { errorNotification, successNotification } from '../services/notification';

const MyTextInput = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <>
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Input className="text-input" {...field} {...props} />
      {meta.touched && meta.error ? (
        <Alert className="error" status={"error"} mt={2}><AlertIcon></AlertIcon>{meta.error}</Alert>
      ) : null}
    </>
  );
};

const MySelect = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <Box>
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Select {...field} {...props} />
      {meta.touched && meta.error ? (
        <Alert className="error" status={"error"} mt={2}><AlertIcon></AlertIcon>{meta.error}</Alert>
      ) : null}
    </Box>
  );
};

// And now we can use these
const CreateCustomerForm = ({ fetchCustomers }) => {
  return (
    <>
      <Formik
      
        initialValues={{
          name: '',
          email: '',
          age: 0,
          gender: '',
        }}
        validationSchema={Yup.object({
          name: Yup.string()
            .max(30, 'Must be 15 characters or less')
            .required('Required'),
          email: Yup.string()
            .email('Invalid email address')
            .required('Required'),
          age: Yup.number()
            .min(5, 'Must be at least 16 years of age')
            .max(100, 'Must be less than 100 years of age')
            .required('Required'),
          gender: Yup.string()
            .oneOf(
              ['Male', 'Female'],
              'Invalid Gender'
            )
            .required('Required'),
        })}
        onSubmit={(customer, { setSubmitting }) => {
          setSubmitting(true);
          // alert(customer);
          saveCustomer(customer).then(res => {
            // alert(JSON.stringify(customer, null, 2));
            // alert("customer saved");
            
            console.log(res);
            successNotification("Customer saved", `${customer.name} was successfully saved`);
            fetchCustomers();
            // console.log(JSON.stringify(customer, null, 2))
          }).catch(err => {
              console.log(err);
              errorNotification(err.code, err.response.data.message);
          }).finally(() => {
              setSubmitting(false);
          })

          // console.log("HI");
        }}
      >
        {
            ({ isValid, isSubmitting }) => (
                <Form>
                    <Stack spacing={"24px"}>
                        <MyTextInput
                            label="Name"
                            name="name"
                            type="text"
                            placeholder="Jane"
                        />

                        <MyTextInput
                            label="Email Address"
                            name="email"
                            type="email"
                            placeholder="jane@formik.com"
                        />

                        <MyTextInput
                            label="Age"
                            name="age"
                            type="number"
                            placeholder="20"
                        />

                        <MySelect label="Gender" name="gender">
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </MySelect>

                        <Button isDisabled={!isValid || isSubmitting} type="submit">Submit</Button>
                    </Stack>
                </Form>
            )
        }
      </Formik>
    </>
  );
};

export default CreateCustomerForm;