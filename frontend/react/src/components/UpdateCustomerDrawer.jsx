import { Button,
        useDisclosure,
        Drawer,
        DrawerOverlay,
        DrawerBody,
        DrawerContent,
        DrawerCloseButton,
        DrawerHeader,
        Input,
        DrawerFooter
} from "@chakra-ui/react"

import { updateCustomer } from "../services/client";
import UpdateCustomerForm from "./UpdateCustomerForm";

const CloseIcon = () => "x";

const UpdateCustomerDrawer = ({ fetchCustomers, initialValues, customerId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button mt={0} bg={'green.400'} color={'white'} rounded={'full'} _hover={{
              transform: 'translateY(-2px)}}',
              boxShadow: 'lg'
          }}
              _focus={{
                bg: 'green.500'
              }}
              onClick={ () => {
                updateCustomer();
                onOpen(); 
              } }
              >
            Update
          </Button>

        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
          <DrawerOverlay />
          <DrawerContent>
            <DrawerCloseButton />
            <DrawerHeader>Update a customer</DrawerHeader>
  
            <DrawerBody>
              <UpdateCustomerForm fetchCustomers={fetchCustomers} initialValues={initialValues} customerId={customerId}></UpdateCustomerForm>
            </DrawerBody>
  
            <DrawerFooter>
            <Button leftIcon={<CloseIcon></CloseIcon>} colorScheme="teal" onClick={onClose}>
              Close
            </Button>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
    </>
    
}

export default UpdateCustomerDrawer;
