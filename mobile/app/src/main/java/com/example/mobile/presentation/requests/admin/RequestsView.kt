package com.example.mobile.presentation.requests.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
import com.example.mobile.domain.models.RequestStatus
import com.example.mobile.domain.models.SupportRequest
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Grey
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// --- Cores do Tema ---
val IPCA_Green_Dark = Color(0xFF004E36)
val IPCA_Gold = Color(0xFF8D764F)
val Background_Light = Color(0xFFF8F9FA)
val Text_Black = Color(0xFF333333)
val Warning_Orange = Color(0xFFE65100)
val Alert_Red = Color(0xFFB00020)





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestsView(
    onMenuClick: () -> Unit,
    onTicketClick: (String) -> Unit,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Pendentes, 1 = Histórico

    // Filter Logic
    val filteredList = state.requests.filter { request ->
        if (selectedTab == 0) {
           request.status == RequestStatus.PENDENTE
        } else {
            request.status == RequestStatus.CANCELADO || request.status == RequestStatus.ENTREGUE || request.status == RequestStatus.APROVADO
        }
    }.sortedByDescending { it.date }

    Scaffold(
        containerColor = Background_Light
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // 1. Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .padding(top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_ipca),
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Pedidos de Apoio",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Gestão de Pedidos",
                            color = Color(0xFFA0C4B5),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = IPCA_Green_Dark,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = IPCA_Gold,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Pendentes", fontWeight = if(selectedTab==0) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Text_Grey
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Histórico", fontWeight = if(selectedTab==1) FontWeight.Bold else FontWeight.Normal) },
                    selectedContentColor = IPCA_Gold,
                    unselectedContentColor = Text_Grey
                )
            }

            // Error Message
            if (state.error != null) {
                Box(modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color.Red.copy(alpha = 0.1f))) {
                    Text(text = state.error ?: "", color = Color.Red, modifier = Modifier.padding(8.dp))
                }
            }

            // 3. List
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = IPCA_Green_Dark)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (filteredList.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = if(selectedTab == 0) "Sem pedidos pendentes" else "Sem histórico",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 32.dp)
                                )
                            }
                        }
                    } else {
                        items(filteredList) { request ->
                            RequestCard(
                                request = request,
                                onStatusChange = { newStatus ->
                                    viewModel.updateStatus(request.id, newStatus)
                                },
                                onClick = { onTicketClick(request.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestCard(
    request: SupportRequest,
    onStatusChange: (RequestStatus) -> Unit,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Date Formatter
    val formattedDate = remember(request.date) {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(request.date)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            if (date != null) outputFormat.format(date) else request.date
        } catch (e: Exception) {
            request.date.take(16).replace("T", " ")
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row 1: Status (Clickable to change) & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    StatusBadge(status = request.status, onClick = { expanded = true })
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        RequestStatus.values().forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.name) },
                                onClick = {
                                    onStatusChange(status)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Text(text = formattedDate, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 2: Observation
            if (!request.observation.isNullOrBlank()) {
                Text(
                    text = request.observation,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Text_Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Row 3: Student Info (Placeholder as we only have ID)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Estudante #${request.studentId.take(8)}...",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.weight(1f))
                 Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = IPCA_Gold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: RequestStatus, onClick: () -> Unit) {
    val (color, text, icon) = when (status) {
        RequestStatus.PENDENTE -> Triple(Warning_Orange, "Pendente", Icons.Default.WarningAmber)
        RequestStatus.ENTREGUE -> Triple(IPCA_Green_Dark, "Entregue", Icons.Default.CheckCircle)
        RequestStatus.CANCELADO -> Triple(Alert_Red, "Cancelado", Icons.Default.Block)
        RequestStatus.APROVADO -> Triple(IPCA_Gold, "Aprovado", Icons.Default.CheckCircle)
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AdminListPreview() {
    RequestsView(onMenuClick = {}, onTicketClick = {})
}