package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.AppScreen
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    post: Post,
    onNavigate: Navigate,
    onDeletePost: (Long?) -> Unit,
//    onFavoriteClick: (postId: Long?) -> Unit
    onFavoriteClick: (Post?) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.small_padding))
            .combinedClickable(
                onClick = { onNavigate(AppScreen.PostDetailsScreen(postId = post.postId.toString())) },
                onLongClick = {
                    openDialog = true
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.default_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        if (openDialog) {
            AppAlertDialog(
                onConfirm = {
                    openDialog = it
                    onDeletePost(post.postId)
                },
                onDismiss = { openDialog = it }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = post.imageUrl,
                contentDescription = null,
                loading = placeholder(R.drawable.placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                    .size(dimensionResource(id = R.dimen.default_image_size))
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = stringResource(id = R.string.blog_title, post.blogTitle)
                )
                Text(
                    text = stringResource(id = R.string.post_summary, post.summary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            FavoriteIconButton(
//                onFavoriteClick = onFavoriteClick,
                onFavoriteClick = onFavoriteClick,
                isFavorite = post.isFavorite,
                post = post,
                postId = post.postId
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppCardPreview() {
    TumblrDemoTheme {
        AppCard(
            post = MockData().postEntities[0],
            onDeletePost = {},
            onNavigate = {}
        ) {}
    }
}
