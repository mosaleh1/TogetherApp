package tech.mosaleh.together.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import kotlinx.coroutines.flow.callbackFlow
import tech.mosaleh.together.R
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.model.CaseType

@Composable
fun CaseCard(case: Case, onClick: () -> Unit) {

    val painter = rememberImagePainter(
        data = case.imageUrl,
        builder = {
            memoryCachePolicy(policy = CachePolicy.ENABLED)
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.empty)
        }
    )
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painter,
                contentDescription = "Case image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = case.caseName, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = case.caseAddress, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Needs: ${case.caseNeeds}", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview
@Composable
fun CaseCard() {
    val case = Case(
        "",
        "Ali",
        "",
        "18 Kamel Sedqee bab elsharia Cairo Egypt Earth",
        33.5,
        56.2,
        CaseType.Human,
        "Food",
    )
    CaseCard(
        case
    ) {

    }
}
