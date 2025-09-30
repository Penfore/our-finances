package net.sertiva.ourfinances.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.sertiva.ourfinances.domain.model.TransactionType
import net.sertiva.ourfinances.ui.theme.ExpenseRed
import net.sertiva.ourfinances.ui.theme.IncomeGreen
import net.sertiva.ourfinances.ui.theme.OurFinancesTheme
import net.sertiva.ourfinances.ui.viewmodel.AddTransactionViewModel
import net.sertiva.ourfinances.utils.SampleData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
            viewModel.resetState()
        }
    }

    uiState.error?.let { error ->
        LaunchedEffect(error) {
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionTypeSelector(
                selectedType = uiState.type,
                onTypeSelected = viewModel::updateType
            )

            OutlinedTextField(
                value = uiState.title,
                onValueChange = viewModel::updateTitle,
                label = { Text("Title") },
                isError = uiState.titleError != null,
                supportingText = uiState.titleError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.amount,
                onValueChange = viewModel::updateAmount,
                label = { Text("Amount") },
                isError = uiState.amountError != null,
                supportingText = uiState.amountError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = { Text("$", style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier.fillMaxWidth()
            )

            CategoryField(
                value = uiState.category,
                onValueChange = viewModel::updateCategory,
                isError = uiState.categoryError != null,
                errorMessage = uiState.categoryError
            )

            DateField(
                date = uiState.date,
                onDateSelected = viewModel::updateDate
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                label = { Text("Description (optional)") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::saveTransaction,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save Transaction")
                }
            }
        }
    }
}

@Composable
private fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Transaction Type",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TransactionType.entries.forEach { type ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .selectable(
                                selected = selectedType == type,
                                onClick = { onTypeSelected(type) },
                                role = Role.RadioButton
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedType == type,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (type == TransactionType.INCOME) IncomeGreen else ExpenseRed
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (type == TransactionType.INCOME) "Income" else "Expense",
                            color = if (selectedType == type) {
                                if (type == TransactionType.INCOME) IncomeGreen else ExpenseRed
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = SampleData.sampleCategories

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Category") },
            isError = isError,
            supportingText = errorMessage?.let { { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onValueChange(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DateField(
    date: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    OutlinedTextField(
        value = date.format(dateFormatter),
        onValueChange = { }, // Read-only field
        label = { Text("Date") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                // TODO: Implement DatePicker dialog
                // For now, just use current date
                onDateSelected(LocalDateTime.now())
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun AddTransactionScreenPreview() {
    OurFinancesTheme {
        // Preview would need a mock ViewModel
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TransactionTypeSelector(
                    selectedType = TransactionType.EXPENSE,
                    onTypeSelected = { }
                )

                OutlinedTextField(
                    value = "Grocery Shopping",
                    onValueChange = { },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
